package com.chriswk.bowling

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