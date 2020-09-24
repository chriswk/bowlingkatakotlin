package com.chriswk.bowling

import java.io.File

abstract class Frame(val rolls: IntArray, val frame: Int, val rollIdx: Int) {
    val firstRoll = rolls[rollIdx]
    val secondRoll: Int = rolls[rollIdx + 1]
    val firstBonusRoll: Int = rolls.getOrNull(nextFrame()) ?: 0
    val secondBonusRoll: Int = rolls.getOrNull(nextFrame() + 1) ?: 0
    open fun score(): Int {
        return rolls[rollIdx] + rolls[rollIdx + 1]
    }

    open fun nextFrame(): Int = rollIdx + 2
    override fun toString(): String {
        return "${firstRoll.toBowlingScore()}, ${secondRoll.toBowlingScore()}"
    }
}

class StrikeFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {
    override fun score(): Int {
        return 10 + firstBonusRoll + secondBonusRoll
    }

    override fun nextFrame(): Int = rollIdx + 1
    override fun toString(): String {
        return if (frame == 10) {
            if (firstBonusRoll + secondBonusRoll == 10 && firstBonusRoll != 10) {
                "X, ${SpareFrame(rolls, 11, nextFrame())}"
            } else {
                "X, ${firstBonusRoll.toBowlingScore()}, ${secondBonusRoll.toBowlingScore()}"
            }
        } else {
            "X"
        }
    }
}

class SpareFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {

    override fun score(): Int {
        return 10 + firstBonusRoll
    }

    override fun toString(): String {
        return if (frame == 10) {
            "${firstRoll.toBowlingScore()}, /, ${firstBonusRoll.toBowlingScore()}"
        } else {
            "${firstRoll.toBowlingScore()}, /"
        }
    }
}

class OpenFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {
    override fun score(): Int {
        return rolls[rollIdx] + rolls[rollIdx + 1]
    }
}

class BowlingGame(val scores: String) {
    private val rolls: IntArray = parseRolls().toIntArray()
    private val frames: List<Frame> = (1..10).fold((emptyList<Frame>() to 0)) { (frames, rollIdx), frameIdx ->
        val firstThrow = rolls[rollIdx]
        if (firstThrow == 10) {
            val frame = StrikeFrame(rolls, frameIdx, rollIdx)
            (frames + frame) to frame.nextFrame()
        } else {
            val frame = OpenFrame(rolls, frameIdx, rollIdx)
            if (frame.score() == 10) {
                (frames + SpareFrame(rolls, frameIdx, rollIdx)) to frame.nextFrame()
            } else {
                (frames + frame) to frame.nextFrame()
            }
        }
    }.first
    val score = frames.sumBy { it.score() }

    private fun parseRolls(): List<Int> = scores.split(",").map { it.toInt(10) }

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
    val gameString = File(args[0]).readLines().first()
    println(BowlingGame(gameString).report())
}
