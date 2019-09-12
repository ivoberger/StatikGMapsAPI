package com.ivoberger.statikgmapsapi.core

import org.junit.Assert.assertEquals
import org.junit.Test

class UrlTest {

    @Test
    fun urlGeneration() {
        val staticMap = StatikGMapsUrl("yourApiKey") {
            size = 500 to 250
            center = Location(.0, .0)
            markers = listOf(
                Location(51.507222, -0.1275),
                Location(address = "London"),
                Location(48.8589507, 2.2770204)
            )
            zoom = 4
            scale = 2
        }
        assertEquals(
            "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&scale=2&center=0.0,0.0&zoom=4&markers=51.507222,-0.1275|London|48.8589507,2.2770204",
            staticMap.toString()
        )
    }
}
