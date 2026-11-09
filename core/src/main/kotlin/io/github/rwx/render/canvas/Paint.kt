package io.github.rwx.render.canvas

import io.github.rwx.geometry.Rect
import java.util.*
import kotlin.math.abs
import kotlin.math.ceil

open class Paint {
    private var r: ColorFilter? = null
    private var w: Typeface? = null
    private var x: CanvasBlendMode? = null
    private var y: Boolean = false
    private var z: Float = 1f
    private var A: Float = 1f
    private var B: Locale? = null
    private var ditherEnabled: Boolean = false
    private var subpixelTextEnabled: Boolean = false
    private var filterBitmapEnabled: Boolean = false
    private var stateRevision: Int = 0

    @JvmField
    var b: Boolean = false

    @JvmField
    var c: Float = 0f

    @JvmField
    var d: Float = 0f

    @JvmField
    var e: Float = 0f

    @JvmField
    var f: Int = 0

    @JvmField
    var g: Int = 2

    @JvmField
    var l: Int = 0

    @JvmField
    var m: Style = Style.FILL

    @JvmField
    var n: Int = 0

    @JvmField
    var o: Float = 0f

    @JvmField
    var p: Align? = null

    @JvmField
    var q: Float = 16f

    constructor() : this(0)

    constructor(i2: Int) {
        a()
        a(i2 or 1280)
        A = 1f
        z = 1f
        a(Locale.getDefault())
    }

    constructor(paint: Paint) {
        b(paint)
    }

    class FontMetrics {
        @JvmField
        var a: Float = 0f

        @JvmField
        var b: Float = 0f

        @JvmField
        var c: Float = 0f

        @JvmField
        var d: Float = 0f
    }

    enum class Style(@JvmField val d: Int) {
        FILL(0),
        STROKE(1),
        FILL_AND_STROKE(2),
    }

    enum class Cap(@JvmField val d: Int) {
        BUTT(0),
        ROUND(1),
        SQUARE(2),
    }

    enum class Join(@JvmField val d: Int) {
        MITER(0),
        ROUND(1),
        BEVEL(2),
    }

    enum class Align(@JvmField val d: Int) {
        LEFT(0),
        CENTER(1),
        RIGHT(2),
    }

    open fun a() {
        a(1280)
        n = -1
        m = Style.FILL
        q = 16f
        p = Align.LEFT
        r = null
        w = null
        x = null
        y = false
        z = 1f
        A = 1f
        b = false
        c = 0f
        d = 0f
        e = 0f
        f = 0
        g = 2
        ditherEnabled = false
        subpixelTextEnabled = false
        filterBitmapEnabled = false
        a(Locale.getDefault())
        markStateChanged()
    }

    open fun a(paint: Paint) {
        if (this !== paint) {
            b(paint)
        }
    }

    private fun b(paint: Paint) {
        m = paint.m
        n = paint.n
        q = paint.q
        p = paint.p
        r = paint.r
        w = paint.w
        x = paint.x
        y = paint.y
        z = paint.z
        A = paint.A
        b = paint.b
        c = paint.c
        d = paint.d
        e = paint.e
        f = paint.f
        g = paint.g
        B = paint.B
        o = paint.o
        ditherEnabled = paint.ditherEnabled
        subpixelTextEnabled = paint.subpixelTextEnabled
        filterBitmapEnabled = paint.filterBitmapEnabled
        markStateChanged()
    }

    open fun b(): Int = l

    open fun a(i2: Int) {
        if (l != i2) {
            l = i2
            markStateChanged()
        }
    }

    open fun c(): Boolean = (b() and 1) != 0

    open fun a(value: Boolean) {
        if (value) {
            a(l or 1)
        } else {
            a(l and -2)
        }
    }

    open fun b(value: Boolean) {
        if (ditherEnabled != value) {
            ditherEnabled = value
            markStateChanged()
        }
    }

    open fun c(value: Boolean) {
        if (subpixelTextEnabled != value) {
            subpixelTextEnabled = value
            markStateChanged()
        }
    }

    open fun d(value: Boolean) {
        if (filterBitmapEnabled != value) {
            filterBitmapEnabled = value
            markStateChanged()
        }
    }

    open fun d(): Style = m

    open fun a(style: Style?) {
        val resolvedStyle = style ?: Style.FILL
        if (m != resolvedStyle) {
            m = resolvedStyle
            markStateChanged()
        }
    }

    open fun e(): Int = n

    open fun b(i2: Int) {
        if (n != i2) {
            n = i2
            markStateChanged()
        }
    }

    open fun f(): Int = ArgbColor.a(n)

    open fun c(i2: Int) {
        b(ArgbColor.a(i2, ArgbColor.b(n), ArgbColor.c(n), ArgbColor.d(n)))
    }

    open fun a(i2: Int, i3: Int, i4: Int, i5: Int) {
        b((i2 shl 24) or (i3 shl 16) or (i4 shl 8) or i5)
    }

    open fun g(): Float = o

    open fun a(value: Float) {
        if (o != value) {
            o = value
            markStateChanged()
        }
    }

    // Compat no-op: stroke cap has no backend effect, kept for Java callers.
    open fun a(@Suppress("UNUSED_PARAMETER") cap: Cap?) = Unit

    open fun h(): ColorFilter? = r

    fun getBlendMode(): CanvasBlendMode? = x

    open fun a(colorFilter: ColorFilter?): ColorFilter? {
        if (r !== colorFilter) {
            r = colorFilter
            markStateChanged()
        }
        return colorFilter
    }

    open fun a(blendMode: CanvasBlendMode?): CanvasBlendMode? {
        if (x != blendMode) {
            x = blendMode
            markStateChanged()
        }
        return blendMode
    }

    open fun i(): Typeface? = w

    open fun a(typeface: Typeface?): Typeface? {
        if (w !== typeface) {
            w = typeface
            markStateChanged()
        }
        return typeface
    }

    open fun j(): Align? = p

    open fun a(align: Align?) {
        val resolvedAlign = align ?: Align.LEFT
        if (p != resolvedAlign) {
            p = resolvedAlign
            markStateChanged()
        }
    }

    open fun a(locale: Locale?) {
        requireNotNull(locale) { "locale cannot be null" }
        if (locale == B) {
            return
        }
        B = locale
    }

    open fun k(): Float = q

    open fun b(value: Float) {
        if (q != value) {
            q = value
            markStateChanged()
        }
    }

    open fun l(): Float =
        -GameFontMetrics.lineHeight(k(), typefaceKey()) * 0.8f

    open fun m(): Float =
        GameFontMetrics.lineHeight(k(), typefaceKey()) * 0.2f

    open fun a(fontMetrics: FontMetrics?): Float {
        val lineHeight = GameFontMetrics.lineHeight(k(), typefaceKey())
        if (fontMetrics != null) {
            fontMetrics.a = -lineHeight
            fontMetrics.b = -lineHeight * 0.8f
            fontMetrics.c = lineHeight * 0.2f
            fontMetrics.d = lineHeight * 0.25f
        }
        return lineHeight
    }

    open fun n(): FontMetrics {
        val fontMetrics = FontMetrics()
        a(fontMetrics)
        return fontMetrics
    }

    open fun a(str: String?): Float {
        requireNotNull(str) { "text cannot be null" }
        if (str.isEmpty()) {
            return 0f
        }
        if (!y) {
            return ceil(a(str, g).toDouble()).toFloat()
        }
        val originalSize = k()
        b(originalSize * z)
        val measured = a(str, g)
        b(originalSize)
        return ceil((measured * A).toDouble()).toFloat()
    }

    private fun a(str: String, i2: Int): Float =
        GameFontMetrics.textWidth(str, k(), typefaceKey())

    open fun a(chars: CharArray?, i2: Int, i3: Int, maxWidth: Float, measuredWidth: FloatArray?): Int {
        requireNotNull(chars) { "text cannot be null" }
        if (i2 < 0 || chars.size - i2 < abs(i3)) {
            throw ArrayIndexOutOfBoundsException()
        }
        if (chars.isEmpty() || i3 == 0) {
            return 0
        }
        if (!y) {
            return a(chars, i2, i3, maxWidth, g, measuredWidth)
        }
        val originalSize = k()
        b(originalSize * z)
        val measured = a(chars, i2, i3, maxWidth * z, g, measuredWidth)
        b(originalSize)
        if (measuredWidth != null) {
            measuredWidth[0] = measuredWidth[0] * A
        }
        return measured
    }

    private fun a(chars: CharArray, i2: Int, i3: Int, maxWidth: Float, i4: Int, measuredWidth: FloatArray?): Int {
        val count = abs(i3)
        if (count == 0) {
            if (measuredWidth != null) {
                measuredWidth[0] = 0f
            }
            return 0
        }
        val text = chars.concatToString(i2, i2 + count)
        return breakTextMeasured(text, maxWidth, measuredWidth)
    }

    private fun a(str: String, measureForwards: Boolean, maxWidth: Float, i2: Int, measuredWidth: FloatArray?): Int =
        if (!measureForwards) {
            breakTextMeasured(str.reversed(), maxWidth, measuredWidth)
        } else {
            breakTextMeasured(str, maxWidth, measuredWidth)
        }

    private fun a(charSequence: CharSequence, i2: Int, i3: Int, chars: CharArray, i4: Int) {
        when (charSequence) {
            is String -> charSequence.toCharArray(chars, i4, i2, i3)
            is StringBuffer -> charSequence.getChars(i2, i3, chars, i4)
            is StringBuilder -> charSequence.getChars(i2, i3, chars, i4)
            else -> {
                var outIndex = i4
                for (index in i2 until i3) {
                    chars[outIndex] = charSequence[index]
                    outIndex++
                }
            }
        }
    }

    open fun a(
        charSequence: CharSequence?,
        i2: Int,
        i3: Int,
        measureForwards: Boolean,
        maxWidth: Float,
        measuredWidth: FloatArray?,
    ): Int {
        requireNotNull(charSequence) { "text cannot be null" }
        if ((i2 or i3 or (i3 - i2) or (charSequence.length - i3)) < 0) {
            throw IndexOutOfBoundsException()
        }
        if (charSequence.isEmpty() || i2 == i3) {
            return 0
        }
        if (i2 == 0 && charSequence is String && i3 == charSequence.length) {
            return a(charSequence, measureForwards, maxWidth, measuredWidth)
        }
        val chars = TemporaryBuffer.a(i3 - i2)
        a(charSequence, i2, i3, chars, 0)
        val measured = if (measureForwards) {
            a(chars, 0, i3 - i2, maxWidth, measuredWidth)
        } else {
            a(chars, 0, -(i3 - i2), maxWidth, measuredWidth)
        }
        TemporaryBuffer.a(chars)
        return measured
    }

    open fun a(str: String?, measureForwards: Boolean, maxWidth: Float, measuredWidth: FloatArray?): Int {
        requireNotNull(str) { "text cannot be null" }
        if (str.isEmpty()) {
            return 0
        }
        if (!y) {
            return a(str, measureForwards, maxWidth, g, measuredWidth)
        }
        val originalSize = k()
        b(originalSize * z)
        val measured = a(str, measureForwards, maxWidth * z, g, measuredWidth)
        b(originalSize)
        if (measuredWidth != null) {
            measuredWidth[0] = measuredWidth[0] * A
        }
        return measured
    }

    open fun a(chars: CharArray?, i2: Int, i3: Int, widths: FloatArray?): Int {
        requireNotNull(chars) { "text cannot be null" }
        requireNotNull(widths) { "widths cannot be null" }
        if ((i2 or i3) < 0 || i2 + i3 > chars.size || i3 > widths.size) {
            throw ArrayIndexOutOfBoundsException()
        }
        if (chars.isEmpty() || i3 == 0) {
            return 0
        }
        if (!y) {
            return fillTextWidths(chars, i2, i3, widths)
        }
        val originalSize = k()
        b(originalSize * z)
        val measured = fillTextWidths(chars, i2, i3, widths)
        b(originalSize)
        for (index in 0 until measured) {
            widths[index] = widths[index] * A
        }
        return measured
    }

    open fun a(str: String?, i2: Int, i3: Int, rect: Rect?) {
        requireNotNull(str) { "text cannot be null" }
        if ((i2 or i3 or (i3 - i2) or (str.length - i3)) < 0) {
            throw IndexOutOfBoundsException()
        }
        if (rect == null) {
            throw NullPointerException("need bounds Rect")
        }
        rect.set(0, 0, 0, q.toInt())
    }

    private fun typefaceKey(): String? = w?.key

    private fun breakTextMeasured(text: String, maxWidth: Float, measuredWidth: FloatArray?): Int {
        if (maxWidth <= 0f) {
            if (measuredWidth != null) {
                measuredWidth[0] = 0f
            }
            return 0
        }
        var width = 0f
        var count = 0
        for (index in text.indices) {
            var nextWidth = GameFontMetrics.textWidth(text.substring(index, index + 1), k(), typefaceKey())
            if (nextWidth <= 0f) {
                nextWidth = k()
            }
            if (count > 0 && width + nextWidth > maxWidth) {
                break
            }
            width += nextWidth
            count++
            if (width > maxWidth) {
                break
            }
        }
        if (measuredWidth != null) {
            measuredWidth[0] = width
        }
        return count
    }

    private fun fillTextWidths(chars: CharArray, start: Int, count: Int, widths: FloatArray): Int {
        for (index in 0 until count) {
            val width = GameFontMetrics.textWidth(
                chars.concatToString(start + index, start + index + 1),
                k(),
                typefaceKey()
            )
            widths[index] = if (width > 0f) width else k()
        }
        return count
    }

    fun flags(): Int = b()

    fun setFlags(flags: Int) = a(flags)

    fun isAntiAlias(): Boolean = c()

    fun setAntiAlias(value: Boolean) = a(value)

    fun isDither(): Boolean = ditherEnabled

    fun setDither(value: Boolean) = b(value)

    fun isSubpixelText(): Boolean = subpixelTextEnabled

    fun setSubpixelText(value: Boolean) = c(value)

    fun isFilterBitmap(): Boolean = filterBitmapEnabled

    fun setFilterBitmap(value: Boolean) = d(value)

    fun style(): Style = d()

    fun setStyle(style: Style?) = a(style)

    fun color(): Int = e()

    fun setColor(color: Int) = b(color)

    fun alpha(): Int = f()

    fun setAlpha(alpha: Int) = c(alpha)

    fun strokeWidth(): Float = g()

    fun setStrokeWidth(width: Float) = a(width)

    fun colorFilter(): ColorFilter? = h()

    fun setColorFilter(colorFilter: ColorFilter?): ColorFilter? = a(colorFilter)

    fun typeface(): Typeface? = i()

    fun setTypeface(typeface: Typeface?): Typeface? = a(typeface)

    fun textAlign(): Align? = j()

    fun setTextAlign(align: Align?) = a(align)

    fun textSize(): Float = k()

    fun setTextSize(size: Float) = b(size)

    fun getStateRevision(): Int = stateRevision

    protected fun markBackendStateChanged() {
        markStateChanged()
    }

    private fun markStateChanged() {
        stateRevision++
    }
}
