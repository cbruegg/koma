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
import koma.internal.default.utils.reduceArrayAxis
import koma.internal.default.utils.argMinGeneric
import koma.internal.default.utils.argMaxGeneric
import koma.ndarray.NDArray
import koma.ndarray.GenericNDArrayFactory
import koma.internal.default.utils.nIdxToLinear
import koma.pow
import koma.matrix.Matrix

@Suppress("UNCHECKED_CAST")
@koma.internal.JvmName("toMatrixGeneric")
fun <T> NDArray<T>.toMatrixOrNull(): Matrix<T>? {
    
    val ele = this.toIterable().iterator().next()
    return when (ele) {
        is Double -> { try{(this as NDArray<Double>).toMatrix() as Matrix<T>}catch(e:Exception){null}}
        is Float -> { try{(this as NDArray<Float>).toMatrix() as Matrix<T>}catch(e:Exception){null}}
        is Int -> { try{(this as NDArray<Int>).toMatrix() as Matrix<T>}catch(e:Exception){null}}
        else -> { null }
    }
}

@koma.internal.JvmName("fillGeneric")
fun <T> NDArray<T>.fill(f: (idx: IntArray) -> T) = apply {
    for ((nd, linear) in this.iterateIndices())
        this.setGeneric(linear, f(nd))
}

@koma.internal.JvmName("fillGenericBoth")
fun <T> NDArray<T>.fillBoth(f: (nd: IntArray, linear: Int) -> T) = apply {
    for ((nd, linear) in this.iterateIndices())
        this.setGeneric(linear, f(nd, linear))
}

@koma.internal.JvmName("fillGenericLinear")
fun <T> NDArray<T>.fillLinear(f: (idx: Int) -> T) = apply {
    for (idx in 0 until size)
        this.setGeneric(idx, f(idx))
}

@koma.internal.JvmName("createGeneric")
fun <T> GenericNDArrayFactory<T>.create(vararg lengths: Int, filler: (idx: IntArray) -> T)
    = NDArray.createGeneric<T>(*lengths, filler=filler)


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
@koma.internal.JvmName("reshapeGeneric")
inline fun <reified T> NDArray<T>.reshape(vararg dims: Int): NDArray<T> {
    if (dims.reduce { a, b -> a * b } != size)
        throw IllegalArgumentException("$size items cannot be reshaped to ${dims.toList()}")
    var idx = 0
    return NDArray.createGeneric(*dims) { _ -> getGeneric(idx++) }
}


/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapGeneric")
inline fun <T, reified R> NDArray<T>.map(crossinline f: (T) -> R)
    = NDArray.createLinear(*shape().toIntArray(), filler={ f(this.getGeneric(it)) } )

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
@koma.internal.JvmName("mapIndexedGeneric")
fun <T> NDArray<T>.mapIndexed(f: (idx: Int, ele: T) -> T)
    = NDArray.createGeneric<T>(*shape().toIntArray(), filler= { idx-> f(nIdxToLinear(idx), this.getGeneric(*idx)) })


/**
 * Takes each element in a NDArray and passes them through f.
 *
 * @param f A function that takes in an element
 *
 */
@koma.internal.JvmName("forEachGeneric")
fun <T> NDArray<T>.forEach(f: (ele: T) -> Unit) {
    // TODO: Change this back to iteration once there are non-boxing iterators
    for (idx in 0 until size)
        f(getGeneric(idx))
}
/**
 * Takes each element in a NDArray and passes them through f. Index given to f is a linear
 * index, depending on the underlying storage major dimension.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the linear index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedGeneric")
fun <T> NDArray<T>.forEachIndexed(f: (idx: Int, ele: T) -> Unit) {
    // TODO: Change this back to iteration once there are non-boxing iterators
    for (idx in 0 until size)
        f(idx, getGeneric(idx))
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
@koma.internal.JvmName("mapIndexedNGeneric")
fun <T> NDArray<T>.mapIndexedN(f: (idx: IntArray, ele: T) -> T): NDArray<T>
    = NDArray.createGeneric<T>(*shape().toIntArray(), filler = { idx -> f(idx, this.getGeneric(*idx)) })


/**
 * Takes each element in a NDArray and passes them through f. Index given to f is the full
 * ND index of the element.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the ND index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedNGeneric")
fun <T> NDArray<T>.forEachIndexedN(f: (idx: IntArray, ele: T) -> Unit) {
    for ((nd, linear) in iterateIndices())
        f(nd, getGeneric(linear))
}

/**
 * Converts this NDArray into a one-dimensional Array in row-major order.
 */
inline fun <reified T> NDArray<T>.toTypedArray() = Array(size) { getGeneric(it) }

@koma.internal.JvmName("getRangesGeneric")
operator fun <T> NDArray<T>.get(vararg indices: IntRange): NDArray<T> {
    checkIndices(indices.map { it.last }.toIntArray())
    return DefaultGenericNDArray<T>(shape = *indices
            .map { it.last - it.first + 1 }
            .toIntArray()) { newIdxs ->
        val offsets = indices.map { it.first }
        val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
        this.getGeneric(*oldIdxs.toIntArray())
    }
}

@koma.internal.JvmName("setGeneric")
operator fun <T> NDArray<T>.set(vararg indices: Int, value: NDArray<T>) {
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

/**
 * Find the linear index of the minimum element in this array.
 */
@koma.internal.JvmName("argMinGeneric")
fun <T: Comparable<T>> NDArray<T>.argMin(): Int = argMinInternal()

/**
 * Find the linear index of the minimum element along one axis of this array,  returning the result in a new array.
 * If the array contains non-comparable values, this throws an exception.
 * 
 * @param axis      the axis to compute the minimum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@koma.internal.JvmName("argMinAxisGeneric")
fun <T> NDArray<T>.argMin(axis: Int, keepdims: Boolean): NDArray<Int> =
    reduceArrayAxis(this, { length: Int, get: (Int) -> T -> argMinGeneric(length, get) }, axis, keepdims)

/**
 * Find the value of the minimum element in this array.
 */
@koma.internal.JvmName("minGeneric")
fun <T: Comparable<T>> NDArray<T>.min(): T = minInternal()

/**
 * Find the minimum element along one axis of this array, returning the result in a new array.
 * If the array contains non-comparable values, this throws an exception.
 *
 * @param axis      the axis to compute the minimum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@koma.internal.JvmName("minAxisGeneric")
inline fun <reified T> NDArray<T>.min(axis: Int, keepdims: Boolean): NDArray<T> =
    reduceArrayAxis(this, { length: Int, get: (Int) -> T -> get(argMinGeneric(length, get)) }, axis, keepdims)

/**
 * Find the linear index of the maximum element in this array.
 */
@koma.internal.JvmName("argMaxGeneric")
fun <T: Comparable<T>> NDArray<T>.argMax(): Int = argMaxInternal()

/**
 * Find the linear index of the maximum element along one axis of this array, returning the result in a new array.
 * If the array contains non-comparable values, this throws an exception.
 * 
 * @param axis      the axis to compute the maximum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@koma.internal.JvmName("argMaxAxisGeneric")
fun <T> NDArray<T>.argMax(axis: Int, keepdims: Boolean): NDArray<Int> =
    reduceArrayAxis(this, { length: Int, get: (Int) -> T -> argMaxGeneric(length, get) }, axis, keepdims)

/**
 * Find the value of the maximum element in this array.
 */
@koma.internal.JvmName("maxGeneric")
fun <T: Comparable<T>> NDArray<T>.max(): T = maxInternal()

/**
 * Find the maximum element along one axis of this array, returning the result in a new array.
 * If the array contains non-comparable values, this throws an exception.
 *
 * @param axis      the axis to compute the maximum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@koma.internal.JvmName("maxAxisGeneric")
inline fun <reified T> NDArray<T>.max(axis: Int, keepdims: Boolean): NDArray<T> =
    reduceArrayAxis(this, { length: Int, get: (Int) -> T -> get(argMaxGeneric(length, get)) }, axis, keepdims)

operator fun <T> NDArray<T>.get(vararg indices: Int) = getGeneric(*indices)
operator fun <T> NDArray<T>.set(vararg indices: Int, value: T) = setGeneric(indices=*indices, v=value)


