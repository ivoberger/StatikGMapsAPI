package com.ivoberger.statikgmapsapi.core

/**
 * Container to hold a location either by latitude and longitude or an address
 * Latitude and longitude will be checked for validity, addresses won't.
 */
data class StatikMapsLocation(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null
) {
    init {
        // require coordinates to be set and in their valid ranges or an address to be set
        require(
            (latitude != null && longitude != null) || !address.isNullOrBlank()
        ) { "A location must be specified by latitude and longitude or a valid address" }
        require((latitude != null && address == null) || (latitude == null && longitude == null && address != null)) {
            "A location can't be specified by coordinates and an address"
        }
        require(address != null || (latitude!! in -90.0..90.0 && longitude!! in -180.0..180.0)) { "Latitude must be between -90 and 90, longitude between -180 and 180" }
    }

    override fun toString() = address ?: "$latitude,$longitude"
}


/**
 * Creates a [StatikMapsLocation] from a [Pair] of [Double]
 */
fun Pair<Double, Double>.toLocation() = StatikMapsLocation(first, second)

/**
 * Creates a [List] of [StatikMapsLocation] from a [List] of [Pair] of [Double]
 */
fun List<Pair<Double, Double>>.toLocations() = map { it.toLocation() }

/**
 * Converts a list of [StatikMapsLocation]s to a valid URL parameter string
 */
internal fun List<StatikMapsLocation>.toUrlParam(): String = fold("") { param, location ->
    "${if (param.isNotBlank()) "$param|" else ""}$location"
}
