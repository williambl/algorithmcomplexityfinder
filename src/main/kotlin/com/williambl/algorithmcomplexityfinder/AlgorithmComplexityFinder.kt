package com.williambl.algorithmcomplexityfinder

import kotlin.random.Random
import kotlin.system.measureNanoTime

val attempts: Int = 5
val arrayLength: Int = 50

val algorithms: Map<String, (IntArray) -> Unit> = mapOf(
    Pair("test", { _: IntArray -> println("test") })
)

fun main() {

    val resultsAndAlgorithms: Map<String, MutableList<Long>> = algorithms.map { it.key to mutableListOf<Long>() }.toMap()
    for (algorithm in algorithms) {
        for (i in 0 until attempts) {
            val input: IntArray = IntArray(arrayLength) { Random.nextInt() }
            resultsAndAlgorithms[algorithm.key]?.add(
                measureNanoTime {
                    algorithm.value(input)
                }
            )
        }
    }


    for (pair in resultsAndAlgorithms) {
        println("""
            |${pair.key}:
            |   Average: ${pair.value.average()} nanos
            |   Minimum: ${pair.value.min()} nanos
            |   Maximum: ${pair.value.max()} nanos
            |   Range: ${pair.value.max()?.minus(pair.value.min() ?: 0)} nanos
        """.trimMargin())
    }
}