@file:koma.internal.JvmName("NDArray")
@file:koma.internal.JvmMultifileClass

/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.extensions

import koma.internal.default.generated.ndarray.DefaultGenericNDArray
import koma.internal.default.utils.checkIndices
import koma.internal.default.utils.linearToNIdx
import koma.matrix.doubleFactory
import koma.ndarray.NDArray
import koma.ndarray.NumericalNDArrayFactory
import koma.internal.default.utils.nIdxToLinear
import koma.pow
import koma.matrix.Matrix



@koma.internal.JvmName("fillShort")
inline fun  NDArray<Short>.fill(f: (idx: IntArray) -> Short) = apply {
    for ((nd, linear) in this.iterateIndices())
        this.setShort(linear, f(nd))
}

@koma.internal.JvmName("fillShortBoth")
inline fun  NDArray<Short>.fillBoth(f: (nd: IntArray, linear: Int) -> Short) = apply {
    for ((nd, linear) in this.iterateIndices())
        this.setShort(linear, f(nd, linear))
}

@koma.internal.JvmName("fillShortLinear")
inline fun  NDArray<Short>.fillLinear(f: (idx: Int) -> Short) = apply {
    for (idx in 0 until size)
        this.setShort(idx, f(idx))
}

@koma.internal.JvmName("createShort")
inline fun  NumericalNDArrayFactory<Short>.create(vararg lengths: Int, filler: (idx: IntArray) -> Short)
    = NDArray.shortFactory.zeros(*lengths).fill(filler)


/**
 * Returns a new NDArray with the given shape, populated with the data in this array.
 *
 * @param dims Desired dimensions of the output array.
 *
 * @returns A copy of the elements in this array, shaped to the given number of rows and columns,
 *          such that `this.toList() == this.reshape(*dims).toList()`
 *
 * @throws IllegalArgumentException when the product of all of the given `dims` does not equal [size]
 */
@koma.internal.JvmName("reshapeShort")
fun  NDArray<Short>.reshape(vararg dims: Int): NDArray<Short> {
    if (dims.reduce { a, b -> a * b } != size)
        throw IllegalArgumentException("$size items cannot be reshaped to ${dims.toList()}")
    var idx = 0
    return NDArray.shortFactory.zeros(*dims).fill { _ -> getShort(idx++) }
}


/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapShort")
inline fun <reified R> NDArray<Short>.map(crossinline f: (Short) -> R)
    = NDArray.createLinear(*shape().toIntArray(), filler={ f(this.getShort(it)) } )


/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is a linear index, depending on the underlying storage
 * major dimension.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the linear index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapIndexedShort")
inline fun  NDArray<Short>.mapIndexed(f: (idx: Int, ele: Short) -> Short)
    = NDArray.shortFactory.zeros(*shape().toIntArray()).fillLinear { f(it, this.getShort(it)) }


/**
 * Takes each element in a NDArray and passes them through f.
 *
 * @param f A function that takes in an element
 *
 */
@koma.internal.JvmName("forEachShort")
inline fun  NDArray<Short>.forEach(f: (ele: Short) -> Unit) {
    // TODO: Change this back to iteration once there are non-boxing iterators
    for (idx in 0 until size)
        f(getShort(idx))
}
/**
 * Takes each element in a NDArray and passes them through f. Index given to f is a linear
 * index, depending on the underlying storage major dimension.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the linear index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedShort")
inline fun  NDArray<Short>.forEachIndexed(f: (idx: Int, ele: Short) -> Unit) {
    // TODO: Change this back to iteration once there are non-boxing iterators
    for (idx in 0 until size)
        f(idx, getShort(idx))
}

/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is the full ND index of the element.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the ND index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapIndexedNShort")
inline fun  NDArray<Short>.mapIndexedN(f: (idx: IntArray, ele: Short) -> Short): NDArray<Short>
    = NDArray.shortFactory.zeros(*shape().toIntArray()).fillBoth { nd, linear -> f(nd, getShort(linear)) }


/**
 * Takes each element in a NDArray and passes them through f. Index given to f is the full
 * ND index of the element.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the ND index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedNShort")
inline fun  NDArray<Short>.forEachIndexedN(f: (idx: IntArray, ele: Short) -> Unit) {
    for ((nd, linear) in iterateIndices())
        f(nd, getShort(linear))
}

/**
 * Converts this NDArray into a one-dimensional ShortArray in row-major order.
 */
fun  NDArray<Short>.toShortArray() = ShortArray(size) { getShort(it) }

@koma.internal.JvmName("getRangesShort")
operator fun  NDArray<Short>.get(vararg indices: IntRange): NDArray<Short> {
    checkIndices(indices.map { it.last }.toIntArray())
    return DefaultGenericNDArray<Short>(shape = *indices
            .map { it.last - it.first + 1 }
            .toIntArray()) { newIdxs ->
        val offsets = indices.map { it.first }
        val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
        this.getGeneric(*oldIdxs.toIntArray())
    }
}

@koma.internal.JvmName("setShort")
operator fun  NDArray<Short>.set(vararg indices: Int, value: NDArray<Short>) {
    val shape = shape()
    val lastIndex = indices.mapIndexed { i, range -> range + value.shape()[i] }
    val outOfBounds = lastIndex.withIndex().any { it.value > shape()[it.index] }
    if (outOfBounds)
        throw IllegalArgumentException("NDArray with shape ${shape()} cannot be " +
                "set at ${indices.toList()} by a ${value.shape()} array " +
                "(out of bounds)")

    val offset = indices.map { it }.toIntArray()
    value.forEachIndexedN { idx, ele ->
        val newIdx = offset.zip(idx).map { it.first + it.second }.toIntArray()
        this.setGeneric(indices=*newIdx, v=ele)
    }
}


operator fun  NDArray<Short>.get(vararg indices: Int) = getShort(*indices)
operator fun  NDArray<Short>.set(vararg indices: Int, value: Short) = setShort(indices=*indices, v=value)


@koma.internal.JvmName("divShort")
operator fun NDArray<Short>.div(other: Short) = map { (it/other).toShort() }
@koma.internal.JvmName("timesArrShort")
operator fun NDArray<Short>.times(other: NDArray<Short>) = mapIndexedN { idx, ele -> (ele*other.get(*idx)).toShort() }
@koma.internal.JvmName("timesShort")
operator fun NDArray<Short>.times(other: Short) = map { (it * other).toShort() }
@koma.internal.JvmName("unaryMinusShort")
operator fun NDArray<Short>.unaryMinus() = map { (-it).toShort() }
@koma.internal.JvmName("minusShort")
operator fun NDArray<Short>.minus(other: Short) = map { (it - other).toShort() }
@koma.internal.JvmName("minusArrShort")
operator fun NDArray<Short>.minus(other: NDArray<Short>) = mapIndexedN { idx, ele -> (ele - other.get(*idx)).toShort() }
@koma.internal.JvmName("plusShort")
operator fun NDArray<Short>.plus(other: Short) = map { (it + other).toShort() }
@koma.internal.JvmName("plusArrShort")
operator fun NDArray<Short>.plus(other: NDArray<Short>) = mapIndexedN { idx, ele -> (ele + other.get(*idx)).toShort() }
@koma.internal.JvmName("powShort")
infix fun NDArray<Short>.pow(exponent: Int) = map { pow(it.toDouble(), exponent).toShort() }

