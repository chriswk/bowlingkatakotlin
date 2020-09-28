package com.chriswk.bowling

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

class OpenFrame(rolls: IntArray, frame: Int, rollIdx: Int) : Frame(rolls, frame, rollIdx) {}
