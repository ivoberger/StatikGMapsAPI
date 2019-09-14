package com.ivoberger.statikgmapsapi.core

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class RequirementsChecking : StringSpec({
    "Missing size should result in an exception" {
        shouldThrow<IllegalArgumentException> { StatikGMapsUrl("key") {}.toString() }
    }
    "Missing center, zoom, markers, path and visible should result in an exception" {
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") { size = 500 to 500 }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                zoom = 5
            }.toString()
        }
    }
    "Out of range zoom should result in an exception" {
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
                zoom = 21
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
                zoom = -1
            }.toString()
        }
    }
    "Out of range scale should result in an exception" {
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
                zoom = 5
                scale = 0
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
                zoom = 5
                scale = 3
            }.toString()
        }
        // scale 4 is only allowed on premium plans
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
                zoom = 5
                scale = 4
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = StatikMapsLocation(.0, .0)
                zoom = 5
                scale = 3
                premiumPlan = true
            }.toString()
        }

    }
    "Too long urls while not allowing path simplification should result in an exception" {
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                path = (0..799).map { StatikMapsLocation(it % 90.0, it % 180.0) }
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                path = (0..1500).map { StatikMapsLocation(it % 90.0, it % 180.0) }
                encodePath = true
            }.toString()
        }
    }
})
