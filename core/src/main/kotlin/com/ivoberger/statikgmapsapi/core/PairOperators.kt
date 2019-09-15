package com.ivoberger.statikgmapsapi.core

operator fun Pair<Int, Int>.times(other: Int) = first * other to second * other
fun Pair<Int, Int>.lessThanOrEqualTo(other: Int) = first <= other && second <= other
fun Pair<Int, Int>.atLeastOneLargerThan(other: Int) = first > other || second > other
