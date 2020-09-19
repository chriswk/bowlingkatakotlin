package com.chriswk.bowling

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `all zeros score 0`() {
        val allZeros = (1..20).map { "0" }.joinToString(separator = ",")
        val game = BowlingGame(allZeros)
        assertThat(game.score).isEqualTo(0)
    }

    @Test
    fun `all threes score 60`() {
        val allThrees = (1..20).map { "3" }.joinToString(separator = ",")
        val game = BowlingGame(allThrees)
        assertThat(game.score).isEqualTo(60)
    }

    @Test
    fun `frames of nine score 90`() {
        val framesOfNine = (1..10).map { "4,5" }.joinToString(separator = ",")
        val game = BowlingGame(framesOfNine)
        assertThat(game.score).isEqualTo(90)
    }

    @Test
    fun `all spares and nines score 199`() {
        val sparesAndNine = (1..10).map { "9,1" }.joinToString(separator = ",") + ",9"
        val game = BowlingGame(sparesAndNine)
        assertThat(game.score).isEqualTo(190)
    }

    @Test
    fun `alternating spares and strikes score 200`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,10,5,5")
        assertThat(game.score).isEqualTo(200)
    }

    @Test
    fun `A game of all strikes scores 300`() {
        val allStrikes = (1..12).map { "10" }.joinToString(separator = ",")
        assertThat(BowlingGame(allStrikes).score).isEqualTo(300)
    }
    @Test
    fun `A game with alternating strikes and gutters scores 50`() {
        val strikeAndGutter = "10,0,0,10,0,0,10,0,0,10,0,0,10,0,0"
        assertThat(BowlingGame(strikeAndGutter).score).isEqualTo(50)
    }

    @Test
    fun `A game with a strike in the ninth and two strikes and nine in the tenth scores 59`() {
        val oddBall = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,10,9"
        assertThat(BowlingGame(oddBall).score).isEqualTo(59)
    }

}