package com.ivoberger.statikgmapsapi.core

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class UrlGeneration : StringSpec({

    val testValues = object {
        val size = 500 to 250
        val center = Location(.0, .0)
        val locationList = listOf(
            Location(51.507222, -0.1275),
            Location(address = "London"),
            Location(48.8589507, 2.2770204)
        )
        val zoom = 4
        val scale = 2
    }

    var mapUrl: StatikGMapsUrl

    "Basic" {
        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&center=0.0,0.0&zoom=4"

        mapUrl.apply { scale = testValues.scale }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&scale=2&center=0.0,0.0&zoom=4"

        mapUrl = StatikGMapsUrl("yourApiKey") {
            https = false
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
        }
        mapUrl.toString() shouldBe "http://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&center=0.0,0.0&zoom=4"

        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            markers = testValues.locationList
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&markers=51.507222,-0.1275|London|48.8589507,2.2770204"

        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            path = testValues.locationList
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&path=51.507222,-0.1275|London|48.8589507,2.2770204"

        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            visible = testValues.locationList
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&visible=51.507222,-0.1275|London|48.8589507,2.2770204"
    }

    "Custom Image Format" {
        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
            imageFormat = ImageFormat.jpg
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&format=jpg&center=0.0,0.0&zoom=4"
    }

    "Custom Map Type" {
        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
            mapType = MapType.satellite
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&maptype=satellite&center=0.0,0.0&zoom=4"
    }

    "Custom Language" {
        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
            language = "de"
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&language=de&center=0.0,0.0&zoom=4"
    }

    "Custom Region" {
        mapUrl = StatikGMapsUrl("yourApiKey") {
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
            region = "cn"
        }
        mapUrl.toString() shouldBe "https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&region=cn&center=0.0,0.0&zoom=4"
    }

    "Custom baseUrl" {
        mapUrl = StatikGMapsUrl("yourApiKey", baseUrl = "maps.example.com") {
            size = testValues.size
            center = testValues.center
            zoom = testValues.zoom
        }
        mapUrl.toString() shouldBe "https://maps.example.com?key=yourApiKey&size=500x250&center=0.0,0.0&zoom=4"
    }
})
