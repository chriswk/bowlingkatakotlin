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
    fun `all spares and nines score 190`() {
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

    @Test
    fun `A game with all strikes outputs correctly`() {
        val allStrikes = (1..12).map { "10" }.joinToString(separator = ",")
        assertThat(BowlingGame(allStrikes).report()).isEqualTo(
            """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| X | X | X | X | X | X | X | X | X | X, X, X |
            #score: 300
        """.trimMargin("#"))
    }

    @Test
    fun `A game with alternating spares and strikes outputs correctly`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,10,5,5")
        assertThat(game.report()).isEqualTo(
         """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| 5, / | X | 5, / | X | 5, / | X | 5, / | X | 5, / | X, 5, / |
            #score: 200
            """.trimMargin("#")
        )
    }

    @Test
    fun `A game with a strike and open in the 10th outputs correctly`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,10,5,4")
        assertThat(game.report()).isEqualTo(
            """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| 5, / | X | 5, / | X | 5, / | X | 5, / | X | 5, / | X, 5, 4 |
            #score: 199
            """.trimMargin("#")
        )

    }

    @Test
    fun `A game with a spare and strike in the 10th outputs correctly`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,5,5,10")
        assertThat(game.report()).isEqualTo(
            """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| 5, / | X | 5, / | X | 5, / | X | 5, / | X | 5, / | 5, /, X |
            #score: 195
            """.trimMargin("#")
        )

    }

    @Test
    fun `A game with two strikes and an open score in the 10th outputs correctly`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,10,10,5")
        assertThat(game.report()).isEqualTo(
            """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| 5, / | X | 5, / | X | 5, / | X | 5, / | X | 5, / | X, X, 5 |
            #score: 205
            """.trimMargin("#")
        )

    }

    @Test
    fun `Gutter balls gets outputted as '-'`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,10,10,0")
        assertThat(game.report()).isEqualTo(
            """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| 5, / | X | 5, / | X | 5, / | X | 5, / | X | 5, / | X, X, - |
            #score: 200
            """.trimMargin("#")
        )
        val game2 = BowlingGame("0,10,10,5,0,10,5,5,10,5,5,10,5,5,10,10,0")
        assertThat(game2.report()).isEqualTo(
            """
            #| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
            #| -, / | X | 5, - | X | 5, / | X | 5, / | X | 5, / | X, X, - |
            #score: 180
            """.trimMargin("#")
        )

    }

    @Test
    fun `Bowling game has a score function`() {
        val game = BowlingGame("5,5,10,5,5,10,5,5,10,5,5,10,5,5,10,10,0")
        assertThat(game.score).isEqualTo(200)
    }

    @Test
    fun `Score candidates game`() {
        val game = BowlingGame("2, 3, 5, 4, 9, 1, 2, 5, 3, 2, 4, 2, 3, 3, 4, 6, 10, 3, 2")
        assertThat(game.score).isEqualTo(75)
    }

}