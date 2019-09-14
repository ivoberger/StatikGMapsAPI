package com.ivoberger.statikgmapsapi.core

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class Location : StringSpec({
    "Should create a valid location" {
        for (lat in -90..90) {
            for (lng in -180..180) {
                StatikMapsLocation(lat.toDouble(), lng.toDouble())
            }
        }
        StatikMapsLocation(address = "London")
    }
    "Missing parameters should result in an exception" {
        shouldThrow<IllegalArgumentException> { StatikMapsLocation() }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(.0) }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(longitude = .0) }
    }
    "Providing wrong combinations of parameters should result on an exception" {
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(90.0, .0, "London") }
        shouldThrow<IllegalArgumentException> {
            StatikMapsLocation(
                longitude = .0,
                address = "London"
            )
        }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(.0, address = "London") }
    }
    "Out of range coordinates should result in an exception" {
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(91.0, .0) }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(-91.0, .0) }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(.0, 181.0) }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(.0, -181.0) }
    }
    "A non-null but blank or empty address should result in an exception" {
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(address = "") }
        shouldThrow<IllegalArgumentException> { StatikMapsLocation(address = "  ") }
    }
})
