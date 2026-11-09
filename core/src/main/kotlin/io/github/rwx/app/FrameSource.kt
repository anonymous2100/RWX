package io.github.rwx.app

import io.github.rwx.session.GameSession
import io.github.rwx.ui.AppScreen


sealed interface FrameSource {

    data object RwGame : FrameSource
    data object BackgroundBattle : FrameSource
    data object ResumeBackground : FrameSource
    data object RwGameLoading : FrameSource
    data object Empty : FrameSource

    companion object {
        fun from(
            screen: AppScreen,
            isExternalJoinPending: Boolean,
            shouldShowBackgroundBattle: Boolean,
            isStartupMenuBackgroundLoading: Boolean,
            isRwGameLoading: Boolean,
            canResumeForFrame: Boolean,
            gameSession: GameSession,
        ): FrameSource = when {
            isExternalJoinPending ->
                Empty

            screen == AppScreen.InGame ->
                RwGame

            shouldShowBackgroundBattle ->
                BackgroundBattle

            shouldShowResumeMenuBackground(screen, canResumeForFrame) ->
                ResumeBackground

            isStartupMenuBackgroundLoading ||
                    isRwGameLoading ->
                RwGameLoading

            else -> Empty
        }
    }
}
