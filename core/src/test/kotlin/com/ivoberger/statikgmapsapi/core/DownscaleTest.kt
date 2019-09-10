package com.ivoberger.statikgmapsapi.core

import org.junit.Assert.*
import org.junit.Test

class DownscaleTest {
    @Test
    fun skipIfNotNecessary() {
        var origSize = 300 to 170
        val url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = (.0 to .0).toLocation()
            zoom = 14
        }
        url.toString()
        assertEquals(origSize, url.size)
        origSize = 700 to 170
        url.apply {
            size = origSize
            premiumPlan = true
        }.toString()
        assertEquals(origSize, url.size)
    }

    @Test
    fun withoutPremium() {
        val origSize = 70 to 770
        val origRatio = origSize.first / origSize.second.toFloat()
        val url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = (.0 to .0).toLocation()
            zoom = 14
        }
        url.toString()
        val downscaledRatio = url.size!!.first / url.size!!.second.toFloat()
        assertTrue(origRatio - downscaledRatio in -0.05..0.05)
        assertNotEquals(origSize, url.size)
    }

    @Test
    fun withPremium() {
        val origSize = 1000 to 170
        val origRatio = origSize.first / origSize.second.toFloat()
        val url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = (.0 to .0).toLocation()
            zoom = 14
            scale = 4
            premiumPlan = true
        }
        url.toString()
        val downscaledRatio = url.size!!.first / url.size!!.second.toFloat()
        assertTrue(origRatio - downscaledRatio in -0.05..0.05)
        assertNotEquals(origSize, url.size)
    }
}
