package com.chriswk.bowling

import java.io.File
import kotlin.random.Random

class BowlingGame(val scores: String) {
    private val rolls: IntArray = parseRolls().toIntArray()
    private val frames: List<Frame> = (1..10).fold((emptyList<Frame>() to 0)) { (frames, rollIdx), frameIdx ->
        val firstThrow = rolls[rollIdx]
        val secondThrow = rolls[rollIdx+1]
        val thirdThrow = if (rolls.size > rollIdx + 2) { rolls[rollIdx+2] } else null
        val frame = Frame(frameIdx, firstThrow, secondThrow, thirdThrow)
        (frames + frame) to (rollIdx + frame.rollsInFrame())
    }.first
    val score = frames.sumBy { it.score() }

    private fun parseRolls(): List<Int> = scores.split(""",\s?""".toRegex()).map { it.toInt(10) }

    fun report(): String {

        val header = (1..10).joinToString(prefix = "| ", postfix = " |", separator = " | ") {
            "$it"
        }
        val frameScores = frames.joinToString(prefix = "| ", postfix = " |", separator = " | ") {
            it.toString()
        }
        return """#$header
            #$frameScores
            #score: $score""".trimMargin("#")
    }
}

fun Int.toBowlingScore(): String {
    return when (this) {
        10 -> "X"
        0 -> "-"
        else -> "$this"
    }
}




fun main(args: Array<String>) {
    val gameString = when(args.size) {
        1 -> File(args[0]).readLines().first()
        else -> {
            val seed = Random.nextInt()
            println("Seed $seed")
            randomGame(seed)
        }
    }
    println(BowlingGame(gameString).report())
}
