package com.ivoberger.statikgmapsapi.core


/**
 * Encodes a polyline using Google's polyline algorithm
 * (See http://code.google.com/apis/maps/documentation/polylinealgorithm.html for more information).
 *
 * @receiver path as pair of doubles
 * @return encoded polyline
 */
fun List<Pair<Double, Double>>.encode(): String {
    val result: MutableList<String> = mutableListOf()

    var prevLat = 0
    var prevLong = 0

    for ((lat, lng) in this) {
        val iLat = (lat * 1e5).toInt()
        val iLong = (lng * 1e5).toInt()

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


