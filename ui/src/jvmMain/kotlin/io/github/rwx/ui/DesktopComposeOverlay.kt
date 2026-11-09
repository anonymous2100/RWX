package io.github.rwx.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.rwx.app.AppAction
import io.github.rwx.app.AppSession
import io.github.rwx.ui.platform.PlatformComposeHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch
import javax.swing.SwingUtilities

/** Owns in-frame Compose content from bootstrap through the application session. */
class DesktopComposeOverlay constructor(
    initialState: AppUiState,
    hostFactory: (onDispose: () -> Unit) -> PlatformComposeHost,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
    private val onStateChanged: (AppUiState) -> Unit = {},
) : PlatformComposeHost {
    init { checkEdt() }

    private val state = MutableStateFlow(initialState)
    private var session: AppSession? = null
    private var disableSession: (() -> Unit)? = null
    private var disposed = false
    private val host = hostFactory(::release)

    init {
        host.setContent {
            val snapshot by state.collectAsState()
            ComposeOverlayContent(
                state = snapshot,
                onMenuAction = { session?.dispatch(AppAction.Menu(it)) },
                onSettingsAction = { session?.dispatch(AppAction.Settings(it)) },
                onBack = { session?.dispatch(AppAction.Back) },
                onLevelSelectAction = { revision, action ->
                    session?.dispatch(
                        AppAction.LevelSelect(
                            revision,
                            action
                        )
                    )
                },
                onPauseAction = { session?.dispatch(AppAction.Pause(it)) },
                onMultiplayerAction = { kind, revision, action ->
                    session?.dispatch(
                        AppAction.Multiplayer(
                            kind,
                            revision,
                            action
                        )
                    )
                },
                onBattleRoomAction = { revision, action -> session?.dispatch(AppAction.BattleRoom(revision, action)) },
                onDialogAction = { revision, action -> session?.dispatch(AppAction.Dialog(revision, action)) },
                onLoadingCancel = { session?.dispatch(AppAction.LoadingCancel(it)) },
                onModsAction = { revision, action -> session?.dispatch(AppAction.Mods(revision, action)) },
                onResourceBrowserAction = { revision, action ->
                    session?.dispatch(
                        AppAction.ResourceBrowser(
                            revision,
                            action
                        )
                    )
                },
                onReplaySelectAction = { revision, action ->
                    session?.dispatch(
                        AppAction.ReplaySelect(
                            revision,
                            action
                        )
                    )
                },
                onModWindowAction = { revision, action -> session?.dispatch(AppAction.ModWindow(revision, action)) },
                onGameInput = { event -> session?.dispatch(AppAction.GameInput(event)) },
            )
        }
        // Hidden ComposePanels have no composition yet. Drive visibility independently, and
        // avoid scheduling a native window operation for every loading-progress update.
        var lastVisible: Boolean? = null
        scope.launch {
            state.collect { snapshot ->
                SwingUtilities.invokeLater {
                    if (!disposed) {
                        onStateChanged(snapshot)
                        if (lastVisible != snapshot.showComposeOverlay) {
                            lastVisible = snapshot.showComposeOverlay
                            host.setVisible(snapshot.showComposeOverlay)
                        }
                    }
                }
            }
        }
    }

    fun attach(session: AppSession) {
        checkEdt()
        if (disposed || this.session === session) return
        check(this.session == null) { "The overlay is already attached to a session" }
        this.session = session
        bindState(session.uiState, session::setComposeUiEnabled)
    }

    internal fun bindState(sessionState: StateFlow<AppUiState>, setEnabled: (Boolean) -> Unit) {
        checkEdt()
        if (disposed) return
        check(disableSession == null) { "The overlay state is already bound" }
        disableSession = { setEnabled(false) }
        scope.launch {
            // Enabling is queued on the app dispatcher. Keep the bootstrap screen until its first
            // acknowledgement rather than hiding the window for the session's initial false.
            sessionState.dropWhile { !it.composeEnabled }.collect { state.value = it }
        }
        setEnabled(true)
    }

    override val isVisible: Boolean get() = host.isVisible
    override fun setContent(content: @Composable () -> Unit) {
        checkEdt()
        if (!disposed) host.setContent(content)
    }
    override fun setVisible(visible: Boolean) {
        checkEdt()
        if (!disposed) host.setVisible(visible)
    }
    override fun requestFocus() {
        checkEdt()
        if (!disposed) host.requestFocus()
    }
    override fun dispose() {
        checkEdt()
        if (disposed) return
        try { host.dispose() } finally { release() }
    }

    private fun release() {
        checkEdt()
        if (disposed) return
        disposed = true
        scope.cancel()
        val disable = disableSession
        disableSession = null
        session = null
        disable?.invoke()
    }

    private fun checkEdt() {
        check(SwingUtilities.isEventDispatchThread()) { "Compose overlay lifecycle operations must run on the EDT" }
    }
}
