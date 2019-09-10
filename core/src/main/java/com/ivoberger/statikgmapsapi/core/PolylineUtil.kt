package com.ivoberger.statikgmapsapi.core

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Encodes a polyline using Google's polyline algorithm
 * (See http://code.google.com/apis/maps/documentation/polylinealgorithm.html for more information).
 *
 * @receiver path as pair of doubles
 * @return encoded polyline
 */
fun List<Location>.encode(): String {
    val result: MutableList<String> = mutableListOf()

    var prevLat = 0
    var prevLong = 0

    for ((lat, lng) in this) {
        val iLat = (lat!! * 1e5).toInt()
        val iLong = (lng!! * 1e5).toInt()

        val deltaLat = encodeValue(iLat - prevLat)
        val deltaLong = encodeValue(iLong - prevLong)

        prevLat = iLat
        prevLong = iLong

        result.add(deltaLat)
        result.add(deltaLong)
    }

    return result.joinToString("")
}

private fun encodeValue(value: Int): String {
    // Step 2 & 4
    val actualValue = if (value < 0) (value shl 1).inv() else (value shl 1)

    // Step 5-8
    val chunks: List<Int> = splitIntoChunks(actualValue)

    // Step 9-10
    return chunks.map { (it + 63).toChar() }.joinToString("")
}

private fun splitIntoChunks(toEncode: Int): List<Int> {
    // Step 5-8
    val chunks = mutableListOf<Int>()
    var value = toEncode
    while (value >= 32) {
        chunks.add((value and 31) or (0x20))
        value = value shr 5
    }
    chunks.add(value)
    return chunks
}

/**
 * https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm
 */
internal fun List<Location>.simplify(epsilon: Double): List<Location> {
    // Find the point with the maximum distance
    var dmax = 0.0
    var index = 0
    val end = this.size

    for (i in 1..(end - 2)) {
        val d = perpendicularDistance(this[i], this[0], this[end - 1])
        if (d > dmax) {
            index = i
            dmax = d
        }
    }
    // If max distance is greater than epsilon, recursively simplify
    return if (dmax > epsilon) {
        // Recursive call
        val recResults1: List<Location> = subList(0, index + 1).simplify(epsilon)
        val recResults2: List<Location> = subList(index, end).simplify(epsilon)

        // Build the result list
        listOf(recResults1.subList(0, recResults1.lastIndex), recResults2).flatMap { it.toList() }
    } else {
        listOf(this[0], this[end - 1])
    }
}

private fun perpendicularDistance(
    pt: Location, lineFrom: Location, lineTo: Location
): Double =
    abs(
        (lineTo.longitude!! - lineFrom.longitude!!)
                * (lineFrom.latitude!! - pt.latitude!!)
                - (lineFrom.longitude - pt.longitude!!)
                * (lineTo.latitude!! - lineFrom.latitude)
    ) / sqrt(
        (lineTo.longitude - lineFrom.longitude).pow(2.0)
                + (lineTo.latitude - lineFrom.latitude).pow(2.0)
    )


