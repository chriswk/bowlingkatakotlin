package com.chriswk.bowling

class Frame(val frame: Int, val firstRoll: Int, val secondRoll: Int, val thirdRoll: Int?) {
    fun score(): Int {
        return if (strike() || spare()) {
            firstRoll + secondRoll + (thirdRoll ?: 0)
        } else {
            firstRoll + secondRoll
        }
    }

    val isTenth = frame == 10
    fun strike() = strike(firstRoll)
    fun strike(roll: Int) = roll == 10
    fun spare() = !strike() && spare(firstRoll, secondRoll)
    fun spare(roll1: Int, roll2: Int) = !strike(roll1) && roll1 + roll2 == 10
    fun rollsInFrame() = if (strike()) {
        1
    } else {
        2
    }

    override fun toString(): String {
        return if (isTenth) {
            if (strike()) {
                if (spare(secondRoll, thirdRoll!!)) {
                    "${firstRoll.toBowlingScore()}, ${secondRoll.toBowlingScore()}, /"
                } else {
                    "${firstRoll.toBowlingScore()}, ${secondRoll.toBowlingScore()}, ${thirdRoll.toBowlingScore()}"
                }
            } else if (spare()) {
                "${firstRoll.toBowlingScore()}, /, ${thirdRoll!!.toBowlingScore()}"
            } else {
                "${firstRoll.toBowlingScore()}, ${secondRoll.toBowlingScore()}"
            }
        } else {
            if (strike()) {
                firstRoll.toBowlingScore()
            } else if (spare()) {
                "${firstRoll.toBowlingScore()}, /"
            } else {
                "${firstRoll.toBowlingScore()}, ${secondRoll.toBowlingScore()}"
            }
        }
    }
}