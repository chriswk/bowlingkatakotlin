package com.chriswk.bowling

abstract class Frame(val rolls: IntArray, val frame: Int, val rollIdx: Int) {
    val firstRoll = rolls[rollIdx]
    val secondRoll: Int = rolls[rollIdx + 1]
    abstract fun firstBonusRoll(): Int
    abstract fun secondBonusRoll(): Int
    open fun score(): Int {
        return rolls[rollIdx] + rolls[rollIdx + 1]
    }
    open fun nextFrame(): Int = rollIdx + 2
    override fun toString(): String {
        return "$firstRoll, $secondRoll"
    }
}

open class StrikeFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {
    override fun firstBonusRoll(): Int {
        return rolls[nextFrame()]
    }

    override fun secondBonusRoll(): Int {
        return rolls[nextFrame() + 1]
    }

    override fun score(): Int {
        return 10 + firstBonusRoll() + secondBonusRoll()
    }
    override fun nextFrame(): Int = rollIdx + 1
    override fun toString(): String {
        return if(frame == 10) {
            if (firstBonusRoll() == 10 && secondBonusRoll() == 10) {
                "XXX"
            } else if (firstBonusRoll() + secondBonusRoll() == 10) {
                "X, ${SpareFrame(rolls, 11, nextFrame())}"
            } else {
                "X, ${OpenFrame(rolls, 11, nextFrame())}"
            }
        } else {
            "X"
        }
    }
}

open class SpareFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {
    override fun firstBonusRoll(): Int {
        return rolls[nextFrame()]
    }

    override fun secondBonusRoll(): Int {
        return 0
    }

    override fun score(): Int {
        return 10 + firstBonusRoll()
    }

    override fun toString(): String {
        return if (frame == 10) {
            if(firstBonusRoll() == 10) {
                "$firstRoll, /, X"
            } else {
                "$firstRoll, /, ${firstBonusRoll()}"
            }
        } else {
            "$firstRoll, /"
        }
    }
}

class OpenFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {
    override fun firstBonusRoll(): Int {
        return 0
    }

    override fun secondBonusRoll(): Int {
        return 0
    }

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

    fun report() : String {

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