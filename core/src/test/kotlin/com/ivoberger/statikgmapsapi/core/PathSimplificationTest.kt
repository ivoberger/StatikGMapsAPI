package com.ivoberger.statikgmapsapi.core

import org.junit.Assert.assertTrue
import org.junit.Test

class PathSimplificationTest {

    private val locations = (0..999).map { Location(it % 90.0, it % 180.0) }

    @Test
    fun notNecessary() {
        val url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = listOf(
                Location(.0, .0),
                Location(.0, 10.0),
                Location(5.0, .0),
                Location(7.0, .6)
            )
        }
        assertTrue(url.toString().endsWith(url.path.toUrlParam()))
    }

    @Test
    fun ramerDouglasPeuckerImpl() {
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
    fun automatic() {
        val url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = locations
            simplifyPath = true
        }
        assertTrue(url.toString().endsWith(locations.last().toString()))
        assertTrue(url.toString().length <= 8192)
    }
}
