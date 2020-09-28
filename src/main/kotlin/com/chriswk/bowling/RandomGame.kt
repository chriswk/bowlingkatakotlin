package com.chriswk.bowling

import kotlin.random.Random

fun randomGame(seed: Int): String {
    val random = Random(seed)
    return (0..10).map { frame ->
        val first = random.nextInt(11)
        if (frame== 10) {
            if (first == 10) {
                val second = random.nextInt(11)
                if (second == 10) {
                    RandomFrame(first, second, random.nextInt(11))
                } else {
                    RandomFrame(first, second, random.nextInt(11-second))
                }
            } else {
                val second = random.nextInt(11 - first)
                if (first + second == 10) {
                    RandomFrame(first, second, random.nextInt(11))
                } else {
                    RandomFrame(first, second)
                }
            }
        } else {
            if (first == 10) {
                RandomFrame(first = first)
            } else {
                RandomFrame(first = first, second = random.nextInt(11 - first))
            }
        }

    }.joinToString(separator = ",") { it.toString() }
}