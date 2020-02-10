package com.ivoberger.statikgmapsapi.android

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.ivoberger.statikgmapsapi.core.StatikGMapsUrl
import com.ivoberger.statikgmapsapi.core.StatikMapsLocation

fun Marker.toStatikMapsLocation() = position.toStatikMapsLocation()
fun Location.toStatikMapsLocation() = StatikMapsLocation(latitude, longitude)
fun LatLng.toStatikMapsLocation() = StatikMapsLocation(latitude, longitude)

/**
 * Sets the path from the points of the given [Polyline]
 */
@JvmName(name = "setPathFromPolyline")
fun StatikGMapsUrl.path(polyline: Polyline) =
    run { path = polyline.points.map { StatikMapsLocation(it.latitude, it.longitude) } }

/**
 * Sets the path from the points of the given [PolylineOptions]
 */
@JvmName(name = "setPathFromPolylineOptions")
fun StatikGMapsUrl.path(polyline: PolylineOptions) =
    run { path = polyline.points.map { it.toStatikMapsLocation() } }

/**
 * Sets the path from the points of the given [List] of [LatLng]
 */
@JvmName(name = "setPathFromLatLng")
fun StatikGMapsUrl.path(latLngPath: List<LatLng>) =
    run { path = latLngPath.map { it.toStatikMapsLocation() } }

/**
 * Sets the path from the points of the given [List] of [Location]
 */
@JvmName(name = "setPathFromLocations")
fun StatikGMapsUrl.path(locationPath: List<Location>) =
    run { path = locationPath.map { it.toStatikMapsLocation() } }

/**
 * Adds the given [Marker] to [StatikGMapsUrl.markers]
 */
fun StatikGMapsUrl.marker(marker: Marker) = marker(marker.position)

/**
 * Adds the given [LatLng] to [StatikGMapsUrl.markers]
 */
fun StatikGMapsUrl.marker(marker: LatLng) = markers.add(marker.toStatikMapsLocation())

/**
 * Adds the given [Location] to [StatikGMapsUrl.markers]
 */
fun StatikGMapsUrl.marker(marker: Location) = markers.add(marker.toStatikMapsLocation())

/**
 * Sets [StatikGMapsUrl.markers] to the given [List] of [LatLng]
 */
@JvmName(name = "setMarkersFromLatLng")
fun StatikGMapsUrl.markers(markers: List<LatLng>) {
    this.markers = markers.map { it.toStatikMapsLocation() }.toMutableList()
}

/**
 * Sets [StatikGMapsUrl.markers] to the given [List] of [Location]
 */
@JvmName(name = "setMarkersFromLocations")
fun StatikGMapsUrl.markers(markers: List<Location>) {
    this.markers = markers.map { it.toStatikMapsLocation() }.toMutableList()
}

/**
 * Adds the given [LatLng] to [StatikGMapsUrl.visible]
 */
fun StatikGMapsUrl.visible(latLng: LatLng) = visible.add(latLng.toStatikMapsLocation())

/**
 * Adds the given [Location] to [StatikGMapsUrl.visible]
 */
fun StatikGMapsUrl.visible(location: Location) = visible.add(location.toStatikMapsLocation())
