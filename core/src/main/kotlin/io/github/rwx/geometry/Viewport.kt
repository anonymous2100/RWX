package io.github.rwx.geometry


data class Viewport(val width: Int, val height: Int) {
    init {
        require(width >= 0 && height >= 0) { "Viewport dimensions must be non-negative" }
    }
}
