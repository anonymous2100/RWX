package io.github.rwx.app

import io.github.rwx.geometry.Viewport
import io.github.rwx.session.GameSession
import io.github.rwx.ui.AppScreen

internal class FrameRenderController(
    private val gameSession: GameSession,
    private val shouldShowBackgroundBattle: (AppScreen) -> Boolean,
    private val startupPreparationController: StartupPreparationController,
    private val presenter: GameFramePresenter,
) {
    fun render(
        screen: AppScreen,
        isExternalBattleRoomJoinPending: Boolean,
        canResumeForFrame: Boolean,
        canvasViewport: Viewport,
        deltaSeconds: Float,
    ) {
        val source = FrameSource.from(
            screen = screen,
            isExternalJoinPending = isExternalBattleRoomJoinPending,
            shouldShowBackgroundBattle = shouldShowBackgroundBattle(screen),
            isStartupMenuBackgroundLoading = startupPreparationController.isStartupMenuBackgroundLoading(screen),
            isRwGameLoading = startupPreparationController.isRwGameLoading(screen),
            canResumeForFrame = canResumeForFrame,
            gameSession = gameSession,
        )
        when (source) {
            FrameSource.RwGame ->
                gameSession.updateFrame(canvasViewport, deltaSeconds)

            FrameSource.BackgroundBattle ->
                gameSession.updateFrame(canvasViewport, deltaSeconds, drainVisibleLayerBuffers = true)

            FrameSource.ResumeBackground ->
                resumeBackgroundFrameForSession(gameSession, canvasViewport, deltaSeconds)

            FrameSource.RwGameLoading -> when {
                startupPreparationController.isStartupMenuBackgroundLoading(screen) ->
                    startupPreparationController.renderStartupMenuBackgroundLoadingFrame(canvasViewport, deltaSeconds)

                startupPreparationController.isRwGameLoading(screen) ->
                    startupPreparationController.renderRwGameLoadingFrame(canvasViewport, deltaSeconds)

                else -> Unit
            }

            FrameSource.Empty -> Unit
        }
        startupPreparationController.updateLoadingStatus(screen)
        presenter.present()
    }
}

internal fun resumeBackgroundFrameForSession(
    gameSession: GameSession,
    canvasViewport: Viewport,
    deltaSeconds: Float,
) {
    if (!gameSession.usesFrameCommandRendering) {
        gameSession.updateFrame(
            canvasViewport,
            deltaSeconds,
            drainVisibleLayerBuffers = true,
        )
    }
}
