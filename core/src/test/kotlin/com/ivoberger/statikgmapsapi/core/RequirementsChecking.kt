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
                center = Location(.0, .0)
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
                center = Location(.0, .0)
                zoom = 21
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = Location(.0, .0)
                zoom = -1
            }.toString()
        }
    }
    "Out of range scale should result in an exception" {
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = Location(.0, .0)
                zoom = 5
                scale = 0
            }.toString()
        }
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                center = Location(.0, .0)
                zoom = 5
                scale = 3
            }.toString()
        }

    }
    "Too long urls while not allowing path simplification should result in an exception" {
        shouldThrow<IllegalArgumentException> {
            StatikGMapsUrl("key") {
                size = 500 to 500
                path = (0..799).map { Location(it % 90.0, it % 180.0) }
            }.toString()
        }
    }
})
