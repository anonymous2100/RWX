package com.corrodinggames.rts.gameFramework.graphics

import io.github.rwx.geometry.Rect
import io.github.rwx.geometry.RectF
import io.github.rwx.render.canvas.CanvasBlendMode
import io.github.rwx.render.canvas.Paint
import java.io.File
import java.io.InputStream
import java.util.concurrent.locks.Lock

enum class RenderTargetMode {
    DEFAULT,
    IMMEDIATE,
}

data class GraphicsBackendCapabilities(
    val fixedLayerBufferPixelSize: Int = 0,
    val extraLayerBufferCells: Int = 0,
    val layerBufferScrollPreloadWorldMargin: Int = 0,
    val clearLayerBuffersBeforeCopy: Boolean = false,
    val supportsLayerBufferPreRendering: Boolean = true,
    val supportsSmoothFogLayerBuffers: Boolean = true,
    val requiresFogAtlasLock: Boolean = false,
    val requiresImageTintColorFilter: Boolean = false,
)

fun interface DrawTimeOperation {
    fun draw(graphicsEngine: GraphicsEngine)
}

interface GraphicsEngine {
    fun backendCapabilities(): GraphicsBackendCapabilities = GraphicsBackendCapabilities()

    fun supportsShaderEffects(): Boolean = false

    fun supportsPostProcessing(): Boolean = supportsShaderEffects()

    fun supportsTeamShaders(): Boolean = supportsShaderEffects()

    fun b(texture: Texture?): GraphicsEngine

    fun b(texture: Texture?, mode: RenderTargetMode): GraphicsEngine = b(texture)

    fun a(lock: Lock?)

    fun b(lock: Lock?)

    fun a(operation: DrawTimeOperation?) {
        operation?.draw(this)
    }

    fun a(i: Int): Texture

    fun a(i: Int, z: Boolean): Texture

    fun a(inputStream: InputStream?, z: Boolean): Texture

    fun a(i: Int, i2: Int, z: Boolean): Texture

    fun b(i: Int, i2: Int, z: Boolean): Texture

    fun a(texture: Texture?, f: Float, f2: Float, f3: Float, paint: Paint?)

    fun a(texture: Texture?, rect: Rect?, f: Float, f2: Float, f3: Float, paint: Paint?)

    fun a(texture: Texture?, rect: Rect?, rect2: Rect?, paint: Paint?)

    fun a(texture: Texture?, rect: Rect?, rectF: RectF?, paint: Paint?)

    fun a(texture: Texture?, f: Float, f2: Float, paint: Paint?)

    fun a(texture: Texture?, f: Float, f2: Float, paint: Paint?, f3: Float, f4: Float)

    fun b(texture: Texture?, f: Float, f2: Float, paint: Paint?)

    fun b(texture: Texture?, rect: Rect?, rect2: Rect?, paint: Paint?)

    fun a(rect: Rect?, paint: Paint?)

    fun a(texture: Texture?, rect: Rect?, paint: Paint?)

    fun a(texture: Texture?, rect: Rect?, paint: Paint?, i: Int, i2: Int, i3: Int, i4: Int)

    fun a(texture: Texture?, rectF: RectF?, paint: Paint?, f: Float, f2: Float, i: Int, i2: Int)

    fun b(i: Int)

    fun a(i: Int, mode: CanvasBlendMode?)

    fun a(str: String?, f: Float, f2: Float, paint: Paint?, paint2: Paint?, f3: Float)

    fun a(str: String?, f: Float, f2: Float, paint: Paint?)

    fun b(rect: Rect?, paint: Paint?)

    fun a(z: Boolean)

    fun f()

    fun a(rectF: RectF?, paint: Paint?)

    fun c(rect: Rect?, paint: Paint?)

    fun a(rect: Rect?)

    fun a(rectF: RectF?)

    fun a(f: Float, f2: Float, f3: Float, paint: Paint?)

    fun b(f: Float, f2: Float, f3: Float, paint: Paint?)

    fun a(fArr: FloatArray?, i: Int, i2: Int, paint: Paint?)

    fun i()

    fun j()

    fun k()

    fun l()

    fun a(f: Float, f2: Float, f3: Float)

    fun a(f: Float, f2: Float)

    fun a(f: Float, f2: Float, f3: Float, f4: Float)

    fun b(f: Float, f2: Float)

    fun a(f: Float, f2: Float, f3: Float, f4: Float, paint: Paint?)

    fun m(): Int

    fun n(): Int

    fun a(i: Int, i2: Int)

    fun o()

    fun p()

    fun q()

    fun a(shaderProgram: ShaderProgram?)

    fun a(str: String?, paint: Paint?): Int

    fun b(str: String?, paint: Paint?): Int

    fun r(): Texture

    fun a(texture: Texture?, file: File?)
}
