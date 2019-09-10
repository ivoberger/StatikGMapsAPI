package com.ivoberger.statikgmapsapi.core

/**
 * Container to hold a location either by latitude and longitude or an address
 * Latitude and longitude will be checked for validity, addresses won't.
 */
data class Location(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null
) {
    init {
        // require coordinates to be set and in their valid ranges or an address to be set
        require(
            (latitude != null && longitude != null) || address != null
        ) { "A location must be specified by latitude and longitude or a valid address" }
        require(
            (latitude != null && longitude != null && latitude in -90.0..90.0 && longitude in -180.0..180.0)
                    || address != null
        ) { "Latitude must be between -90 and 90, longitude between -180 and 180" }
    }

    override fun toString() = address ?: "$latitude,$longitude"
}


/**
 * Creates a [Location] from a [Pair] of [Double]
 */
fun Pair<Double, Double>.toLocation() = Location(first, second)

/**
 * Creates a [List] of [Location] from a [List] of [Pair] of [Double]
 */
fun List<Pair<Double, Double>>.toLocations() = map { it.toLocation() }


internal fun List<Location>.toUrlParam(): String = fold("") { param, pair ->
    "${if (param.isNotBlank()) "$param|" else ""}${pair.latitude},${pair.longitude}"
}
