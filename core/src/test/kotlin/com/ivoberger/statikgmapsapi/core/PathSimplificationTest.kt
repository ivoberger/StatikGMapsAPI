package com.ivoberger.statikgmapsapi.core

import org.junit.Assert.assertTrue
import org.junit.Test

class PathSimplificationTest {

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
    fun compressionOnly() {
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
}
