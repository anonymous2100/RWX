package io.github.rwx.slick

import io.github.rwx.app.GameFramePresenter

class SlickFramePresenter(private val presentSnapshot: () -> Unit = {}) : GameFramePresenter {
    override fun present() {
        presentSnapshot()
    }
}
