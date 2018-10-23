/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.internal.default.generated.ndarray

import koma.ndarray.*
import koma.internal.KomaJsName
import koma.internal.default.utils.*


/**
 * An (unoptimized) implementation of [NDArray] in pure Kotlin, for portability between the
 * different platforms koma supports.
 *
 * @param shape A vararg specifying the size of each dimension, e.g. a 3D array with size 4x6x8 would pass in 4,6,8)
 * @param init A function that takes a location in the new array and returns its initial value.
 */
open class DefaultGenericNDArray<T>(@KomaJsName("shape_private") vararg protected val shape: Int,
                             init: ((IntArray)->T)): NDArray<T> {

    /**
     * Underlying storage. Default backends uses a simple array.
     */
    private val storage: Array<T>

    init {
        @Suppress("UNCHECKED_CAST")
        storage = Array(shape.reduce{ a, b-> a * b}, {init.invoke(linearToNIdx(it)) as Any?}) as Array<T>
    }

    override fun getGeneric(vararg indices: Int): T {
        checkIndices(indices)
        return storage[nIdxToLinear(indices)]
    }
    override fun getGeneric(i: Int): T = storage[i]
    override fun setGeneric(i: Int, v: T) { storage[i] = v }

    override fun setGeneric(vararg indices: Int, v: T) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = v
    }
    // TODO: cache this
    override val size get() = storage.size
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<T> = DefaultGenericNDArray(*shape, init = { this.getGeneric(*it) })
    override fun getBaseArray(): Any = storage

    private val wrongType = "Double methods not implemented for generic NDArray"
    override fun getDouble(i: Int): Double {
        val ele = getGeneric(i)
        if (ele is Number)
            return ele.toDouble()
        else
            error(wrongType)
    }
    override fun setDouble(i: Int, v: Double) {
       @Suppress("UNCHECKED_CAST")
       setGeneric(i, v as T)
    }

    override fun getByte(i: Int): Byte {
        val ele = getGeneric(i)
        if (ele is Number)
            return ele.toByte()
        else
            error(wrongType)
    }
    override fun setByte(i: Int, v: Byte) {
       @Suppress("UNCHECKED_CAST")
       setGeneric(i, v as T)
    }

    override fun getInt(i: Int): Int {
        val ele = getGeneric(i)
        if (ele is Number)
            return ele.toInt()
        else
            error(wrongType)
    }
    override fun setInt(i: Int, v: Int) {
       @Suppress("UNCHECKED_CAST")
       setGeneric(i, v as T)
    }

    override fun getFloat(i: Int): Float {
        val ele = getGeneric(i)
        if (ele is Number)
            return ele.toFloat()
        else
            error(wrongType)
    }
    override fun setFloat(i: Int, v: Float) {
       @Suppress("UNCHECKED_CAST")
       setGeneric(i, v as T)
    }

    override fun getLong(i: Int): Long {
        val ele = getGeneric(i)
        if (ele is Number)
            return ele.toLong()
        else
            error(wrongType)
    }
    override fun setLong(i: Int, v: Long) {
       @Suppress("UNCHECKED_CAST")
       setGeneric(i, v as T)
    }

    override fun getShort(i: Int): Short {
        val ele = getGeneric(i)
        if (ele is Number)
            return ele.toShort()
        else
            error(wrongType)
    }
    override fun setShort(i: Int, v: Short) {
       @Suppress("UNCHECKED_CAST")
       setGeneric(i, v as T)
    }


    override fun toString(): String {
        if (size == 0)
            return "[]"
        val s = StringBuilder()
        val index = IntArray(shape.size)
        val lastAxis = shape.size-1
        val width = shape[lastAxis]
        var printBracketsFrom = 0
        while (index[0] < shape[0]) {
            for (i in 0..lastAxis)
                s.append(if (i >= printBracketsFrom) '[' else ' ')
            for (i in 0 until width) {
                index[lastAxis] = i
                s.append(getGeneric(*index))
                if (i < width-1)
                    s.append(", ")
            }
            for (i in lastAxis downTo 0) {
                index[i] += 1
                if (index[i] == shape[i]) {
                    s.append(']')
                    printBracketsFrom = i
                    if (i > 0)
                        index[i] = 0
                    else
                        break
                }
                else
                    break
            }
            if (index[0] < shape[0])
                s.append('\n')
        }
        return s.toString()
    }

    override fun argMinInternal() =
            argMinGeneric(size, { getGeneric(it) })

    override fun argMaxInternal() =
            argMaxGeneric(size, { getGeneric(it) })

    override fun minInternal() =
            getGeneric(argMinGeneric(size, { getGeneric(it) }))

    override fun maxInternal() =
            getGeneric(argMaxGeneric(size, { getGeneric(it) }))

}

