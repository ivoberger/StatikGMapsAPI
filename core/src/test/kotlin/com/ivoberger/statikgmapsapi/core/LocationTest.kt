package com.ivoberger.statikgmapsapi.core

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class LocationTest : StringSpec({
    "Should create a valid location" {
        for (lat in -90..90) {
            for (lng in -180..180) {
                Location(lat.toDouble(), lng.toDouble())
            }
        }
        Location(address = "London")
    }
    "Missing parameters should result in an exception" {
        shouldThrow<IllegalArgumentException> { Location() }
    }
    "Out of range coordinates should result in an exception" {
        shouldThrow<IllegalArgumentException> { Location(91.0, .0) }
        shouldThrow<IllegalArgumentException> { Location(-91.0, .0) }
        shouldThrow<IllegalArgumentException> { Location(.0, 181.0) }
        shouldThrow<IllegalArgumentException> { Location(.0, -181.0) }
    }
    "A non-null but blank or empty address should result in an exception" {
        shouldThrow<IllegalArgumentException> { Location(address = "") }
        shouldThrow<IllegalArgumentException> { Location(address = "  ") }
    }
})
