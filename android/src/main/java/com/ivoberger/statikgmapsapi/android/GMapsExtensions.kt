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

fun List<Location>.toPath() = map { StatikMapsLocation(it.latitude, it.longitude) }

/**
 * Sets the path from the points of the given [Polyline]
 */
fun StatikGMapsUrl.path(polyline: Polyline) =
    run { path = polyline.points.map { StatikMapsLocation(it.latitude, it.longitude) } }

/**
 * Sets the path from the points of the given [PolylineOptions]
 */
fun StatikGMapsUrl.path(polyline: PolylineOptions) =
    run { path = polyline.points.map { it.toStatikMapsLocation() } }

/**
 * Sets the path from the points of the given [List] of [LatLng]
 */
fun StatikGMapsUrl.path(latLngPath: List<LatLng>) =
    run { path = latLngPath.map { it.toStatikMapsLocation() } }

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


fun StatikGMapsUrl.visible(latLng: LatLng) = visible.add(latLng.toStatikMapsLocation())
fun StatikGMapsUrl.visible(location: Location) = visible.add(location.toStatikMapsLocation())
