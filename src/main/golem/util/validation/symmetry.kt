@file:JvmName("SymmetryValidation")

package golem.util.validation
import golem.validateMatrices


/**
 * Require the current matrix to be symmetric.
 */
val ValidationContext.symmetric: ValidationContext get() {
    if (!validateMatrices) return this
    val rows = currentMatrix.numRows()
    val cols = currentMatrix.numCols()
    if (rows != cols) {
        val msg = "${currentMatrixName} must be symmetric, but has dimensions ${rows}x${cols}"
        throw IndexOutOfBoundsException(msg)
    }
    for (row in 0.until(rows))
        for (col in row.until(cols))
            if (currentMatrix[row, col] != currentMatrix[col, row]) {
                val msg = "${currentMatrixName} must be symmetric, but " +
                    "${currentMatrixName}[${row}, ${col}] != ${currentMatrixName}[${col}, ${row}]."
                throw IllegalArgumentException(msg)
            }
    return this
}
