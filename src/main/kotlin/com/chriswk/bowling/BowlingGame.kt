package com.chriswk.bowling

class BowlingGame(val scores : String) {
    private val rolls: IntArray = parseRolls().toIntArray()
    val score = scoreGame()

    private fun scoreGame(): Int {
        return (1..10).fold((0 to 0)) { (sum, rollIdx), frame ->
            if(rolls[rollIdx] == 10) {
                (sum + (strike(rollIdx)) to rollIdx + 1)
            } else {
                val frameScore = rolls[rollIdx] + rolls[rollIdx+1]
                if (frameScore == 10) {
                    ((sum + spare(frameScore, rollIdx+2)) to rollIdx + 2)
                } else {
                    (sum + frameScore) to rollIdx + 2
                }
            }
        }.first
    }

    private fun strike(rollIdx: Int): Int {
        return rolls[rollIdx] + (rolls.getOrNull(rollIdx+1) ?: 0) + (rolls.getOrNull(rollIdx+2) ?: 0)
    }
    private fun spare(score: Int, rollIdx : Int): Int {
        return score + (rolls.getOrNull(rollIdx) ?: 0)
    }
    private fun parseRolls(): List<Int> = scores.split(",").map { it.toInt(10) }
}