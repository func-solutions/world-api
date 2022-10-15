package me.func.world

import ru.cristalix.core.math.V3
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author func 27.09.2020
 * @project MathUtils
 */
object Rotation {

    fun rotate(theta: Double, axis: V3, dot: V3) = multiplyMatrixToVector(
        getRotationMatrixByOrt(theta, axis.x, axis.y, axis.z), doubleArrayOf(dot.x, dot.y, dot.z)
    )

    fun getRotationMatrixByOrt(theta: Double, x: Double, y: Double, z: Double): Array<DoubleArray> {
        val cos = cos(theta)
        val sin = sin(theta)
        val nCos = 1 - cos
        return arrayOf(
            doubleArrayOf(cos + nCos * x * x, nCos * x * y - sin * z, nCos * x * z + sin * y),
            doubleArrayOf(nCos * y * x + sin * z, cos + nCos * y * y, nCos * y * z - sin * x),
            doubleArrayOf(nCos * z * x - sin * y, nCos * z * y + sin * x, cos + nCos * z * z)
        )
    }

    private fun multiplyMatrixToVector(matrix: Array<DoubleArray>, vector: DoubleArray): V3 {
        val newVector = DoubleArray(vector.size)
        for (j in vector.indices) for (i in 0 until matrix[0].size) newVector[j] += matrix[j][i] * vector[i]
        return V3(newVector[0], newVector[1], newVector[2])
    }
}