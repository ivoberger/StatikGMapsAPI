package com.ivoberger.statikgmapsapi.core

import io.kotlintest.matchers.collections.shouldBeLargerThan
import io.kotlintest.matchers.collections.shouldContainInOrder
import io.kotlintest.matchers.numerics.shouldBeLessThanOrEqual
import io.kotlintest.matchers.string.shouldEndWith
import io.kotlintest.matchers.string.shouldNotEndWith
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PathTransformation : StringSpec({

    val locations = (0..999).map { Location(it % 90.0, it % 180.0) }

    "ToString should automatically detect if simplification is necessary"  {
        var url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = listOf(
                Location(.0, .0),
                Location(.0, 10.0),
                Location(5.0, .0),
                Location(7.0, .6)
            )
        }
        url.toString() shouldEndWith url.path.toUrlParam()

        url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = locations
            simplifyPath = true
        }
        val urlString = url.toString()
        urlString shouldEndWith locations.last().toString()
        urlString shouldNotEndWith url.path.toUrlParam()
        urlString.length shouldBeLessThanOrEqual 8192
    }

    "Simplified path should contain" {
        val simplified = locations.simplify(5.0)
        locations shouldContainInOrder simplified
        locations shouldBeLargerThan simplified
    }

    val testPath = listOf(
        Location(41.0, -87.0),
        Location(41.5, -87.0),
        Location(41.5, -87.75),
        Location(40.555, -87.555),
        Location(40.555, -88.0),
        Location(42.0, -88.0)
    )
    // result from https://developers.google.com/maps/documentation/utilities/polylineutility
    val encodedTestPath = "_yfyF~d_rO_t`B??nnqCfqwDwae@?f|uAgfyG?"

    "Encoded path should match result from Google's reference implementation" {
        testPath.encode() shouldBe encodedTestPath
    }

    "Path should be encoded if set" {
        val url = StatikGMapsUrl("placeholder") {
            size = 300 to 170
            path = testPath
            encodePath = true
        }
        url.toString() shouldEndWith encodedTestPath
    }
})
