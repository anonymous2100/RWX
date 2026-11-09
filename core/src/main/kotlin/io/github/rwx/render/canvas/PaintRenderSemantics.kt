package io.github.rwx.render.canvas

fun Int.applyColorFilter(colorFilter: ColorFilter?): Int =
    when (colorFilter) {
        is MultiplyAddColorFilter ->
            if (colorFilter.usesLegacyAdditiveBlend()) {
                applyLegacyLightingMultiply(colorFilter)
            } else {
                applyMultiplyAddColorFilter(colorFilter)
            }

        is BlendColorFilter -> applyBlendColorFilter(colorFilter)
        else -> this
    }

fun Paint?.resolvedPaintColor(defaultColor: Int = -1): Int {
    val color = this?.color() ?: defaultColor
    return color.applyColorFilter(this?.colorFilter())
}

private fun Int.applyLegacyLightingMultiply(colorFilter: MultiplyAddColorFilter): Int =
    argb(
        alpha = multiplyChannel(alphaComponent, colorFilter.multiplyColor.alphaComponent),
        red = multiplyChannel(redComponent, colorFilter.multiplyColor.redComponent),
        green = multiplyChannel(greenComponent, colorFilter.multiplyColor.greenComponent),
        blue = multiplyChannel(blueComponent, colorFilter.multiplyColor.blueComponent),
    )

private fun Int.applyMultiplyAddColorFilter(colorFilter: MultiplyAddColorFilter): Int =
    argb(
        alpha = alphaComponent,
        red = filteredLightingChannel(
            redComponent,
            colorFilter.multiplyColor.redComponent,
            colorFilter.addColor.redComponent
        ),
        green = filteredLightingChannel(
            greenComponent,
            colorFilter.multiplyColor.greenComponent,
            colorFilter.addColor.greenComponent
        ),
        blue = filteredLightingChannel(
            blueComponent,
            colorFilter.multiplyColor.blueComponent,
            colorFilter.addColor.blueComponent
        ),
    )

private fun Int.applyBlendColorFilter(colorFilter: BlendColorFilter): Int =
    when (colorFilter.blendMode) {
        CanvasBlendMode.Multiply -> argb(
            alpha = alphaComponent,
            red = multiplyChannel(redComponent, colorFilter.color.redComponent),
            green = multiplyChannel(greenComponent, colorFilter.color.greenComponent),
            blue = multiplyChannel(blueComponent, colorFilter.color.blueComponent),
        )

        else -> this
    }

private fun filteredLightingChannel(source: Int, multiply: Int, add: Int): Int =
    (multiplyChannel(source, multiply) + add).coerceIn(0, 255)

private fun multiplyChannel(source: Int, multiply: Int): Int = (source * multiply) / 255

private val Int.alphaComponent: Int get() = (this ushr 24) and 0xff

private val Int.redComponent: Int get() = (this ushr 16) and 0xff

private val Int.greenComponent: Int get() = (this ushr 8) and 0xff

private val Int.blueComponent: Int get() = this and 0xff

private fun argb(alpha: Int, red: Int, green: Int, blue: Int): Int =
    ((alpha and 0xff) shl 24) or
            ((red and 0xff) shl 16) or
            ((green and 0xff) shl 8) or
            (blue and 0xff)
