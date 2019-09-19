package com.williambl.algorithmcomplexityfinder

import kotlin.random.Random
import kotlin.system.measureNanoTime


val algorithms: Map<String, (IntArray) -> Unit> = mapOf(
    Pair("printing", { input: IntArray -> input.forEach { println(it) } })
)

fun main(args: Array<String>) {
    val attempts = args.getOrNull(0)?.toInt() ?: 5
    val arrayLength = args.getOrNull(1)?.toInt() ?: 500

    val resultsAndAlgorithms: Map<String, Map<Int, MutableList<Long>>> =
        algorithms.map {
            it.key to List(arrayLength) { index -> index+1 }.map { it1 -> it1 to mutableListOf<Long>() }.toMap()
        }.toMap()

    for (algorithm in algorithms) {
        for (i in 0 until attempts) {
            for (j in 1..arrayLength) {
                val input: IntArray = IntArray(j) { Random.nextInt() }
                resultsAndAlgorithms[algorithm.key]?.get(j)?.add(
                    measureNanoTime {
                        algorithm.value(input)
                    }
                )
            }
        }
    }

    for (pair in resultsAndAlgorithms) {
        println("""
            |${pair.key}:
            |   Average for ${arrayLength/5}-length array: ${pair.value[arrayLength/5]?.average()} nanos
            |   Average for ${arrayLength}-length array: ${pair.value[arrayLength]?.average()} nanos
            |   Difference: ${pair.value[arrayLength]?.average()?.minus(pair.value[arrayLength/5]?.average() ?: 0.0)} nanos
            |   Factor: ${(pair.value[arrayLength]?.average()?.div(pair.value[arrayLength/5]?.average() ?: 0.0))} times
        """.trimMargin())
    }
}