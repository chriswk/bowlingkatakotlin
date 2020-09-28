package com.chriswk.bowling

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