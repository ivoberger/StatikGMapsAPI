package com.ivoberger.statikgmapsapi.core

import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class Downscale : StringSpec({

    "Size should stay the same as it is within limits" {
        var origSize = 300 to 170
        val url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = StatikMapsLocation(.0, .0)
            zoom = 14
        }
        url.toString()
        url.size shouldBe origSize

        origSize = 700 to 170
        url.apply {
            size = origSize
            premiumPlan = true
        }.toString()

        url.size shouldBe origSize
    }

    "Disallowing downscale should not result in an exception when size is within limits" {
        var origSize = 300 to 170
        val url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = StatikMapsLocation(.0, .0)
            zoom = 14
            downscale = false
        }
        url.toString()
        url.size shouldBe origSize

        origSize = 700 to 170
        url.apply {
            size = origSize
            premiumPlan = true
        }.toString()

        url.size shouldBe origSize
    }

    "IllegalArgumentException should be thrown as size exceeds limits but downscaling is forbidden" {
        val url = StatikGMapsUrl("placeholder") {
            center = StatikMapsLocation(address = "London")
            zoom = 14
            downscale = false
        }.apply { size = 700 to 100 }
        shouldThrow<IllegalArgumentException> { url.toString() }
        url.apply {
            size = 2049 to 170
            premiumPlan = true
        }
        shouldThrow<IllegalArgumentException> { url.toString() }
        url.apply {
            size = 500 to 1030
            scale = 2
        }
        shouldThrow<IllegalArgumentException> { url.toString() }
        url.apply {
            size = 500 to 600
            scale = 4
        }
        shouldThrow<IllegalArgumentException> { url.toString() }
    }



    "Size should be downscaled to fit within specs"  {
        val tolerance = .05f
        var origSize = 70 to 770
        var origRatio = origSize.first / origSize.second.toFloat()
        var url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = StatikMapsLocation(.0, .0)
            zoom = 14
        }
        url.toString()

        url.size!!.first / url.size!!.second.toFloat() shouldBe (origRatio plusOrMinus tolerance)
        url.size shouldNotBe origSize

        origSize = 770 to 170
        origRatio = origSize.first / origSize.second.toFloat()
        url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = StatikMapsLocation(.0, .0)
            zoom = 14
        }
        url.toString()

        url.size!!.first / url.size!!.second.toFloat() shouldBe (origRatio plusOrMinus tolerance)
        url.size shouldNotBe origSize

        origSize = 1000 to 170
        origRatio = origSize.first / origSize.second.toFloat()
        url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = StatikMapsLocation(.0, .0)
            zoom = 14
            scale = 4
            premiumPlan = true
        }
        url.toString()

        url.size!!.first / url.size!!.second.toFloat() shouldBe (origRatio plusOrMinus tolerance)
        url.size shouldNotBe origSize

        origSize = 100 to 1000
        origRatio = origSize.first / origSize.second.toFloat()
        url = StatikGMapsUrl("placeholder") {
            size = origSize
            center = StatikMapsLocation(.0, .0)
            zoom = 14
            scale = 4
            premiumPlan = true
        }
        url.toString()

        url.size!!.first / url.size!!.second.toFloat() shouldBe (origRatio plusOrMinus tolerance)
        url.size shouldNotBe origSize
    }
})
