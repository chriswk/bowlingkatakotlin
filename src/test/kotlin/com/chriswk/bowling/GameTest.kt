package com.chriswk.bowling

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `all zeros score 0`() {
        val allZeros = (1..10).map { "0,0" }.joinToString(separator = ",")
        val game = BowlingGame(allZeros)
        assertThat(game.score).isEqualTo(0)
    }
}