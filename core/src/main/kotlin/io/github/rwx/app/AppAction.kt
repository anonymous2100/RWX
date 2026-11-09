package io.github.rwx.app

import io.github.rwx.ui.model.*

sealed interface AppAction {

    data class Menu(val action: MainMenuAction) : AppAction

    data class LevelSelect(
        val revision: Long,
        val action: LevelSelectAction,
    ) : AppAction

    data class Pause(val action: PauseMenuAction) : AppAction

    data class BattleRoom(
        val revision: Long,
        val action: BattleRoomAction,
    ) : AppAction

    data class Mods(
        val revision: Long,
        val action: ModsAction,
    ) : AppAction

    data class ResourceBrowser(
        val revision: Long,
        val action: ResourceBrowserAction,
    ) : AppAction

    data class ReplaySelect(
        val revision: Long,
        val action: ReplaySelectAction,
    ) : AppAction

    data class Multiplayer(
        val lobbyKind: MultiplayerLobbyKind,
        val revision: Long,
        val action: MultiplayerAction,
    ) : AppAction

    data class Dialog(
        val revision: Long,
        val action: DialogUiAction,
    ) : AppAction

    data class LoadingCancel(val revision: Long) : AppAction

    data class ModWindow(
        val revision: Long,
        val action: ModWindowAction,
    ) : AppAction

    data object Back : AppAction

    data class Settings(val action: SettingsUiAction) : AppAction

    data class GameInput(val event: GameInputEvent) : AppAction
}

