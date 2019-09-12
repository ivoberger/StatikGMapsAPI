package com.ivoberger.statikgmapsapi.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PathTransformationTests {

    private val locations = (0..999).map { Location(it % 90.0, it % 180.0) }

    @Test
    fun simplificationCheck() {
        var url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = listOf(
                Location(.0, .0),
                Location(.0, 10.0),
                Location(5.0, .0),
                Location(7.0, .6)
            )
        }
        assertTrue(url.toString().endsWith(url.path.toUrlParam()))

        url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = locations
            simplifyPath = true
        }
        val urlString = url.toString()
        assertTrue(urlString.endsWith(locations.last().toString()))
        assertTrue(urlString.length <= 8192)
    }

    @Test
    fun simplification() {
        val simplified = locations.simplify(5.0)
        assertTrue(
            "The simplified path contains locations not present in the source",
            locations.containsAll(simplified)
        )
        assertTrue(
            "The simplified path isn't shorter than the source",
            locations.size > simplified.size
        )
    }

    @Test
    fun encoding() {
        val path = listOf(
            Location(41.0, -87.0),
            Location(41.5, -87.0),
            Location(41.5, -87.75),
            Location(40.555, -87.555),
            Location(40.555, -88.0),
            Location(42.0, -88.0)
        )
        // test against result from https://developers.google.com/maps/documentation/utilities/polylineutility
        assertEquals("_yfyF~d_rO_t`B??nnqCfqwDwae@?f|uAgfyG?", path.encode())
    }
}
