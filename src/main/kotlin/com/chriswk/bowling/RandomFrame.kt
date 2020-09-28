package com.chriswk.bowling

data class RandomFrame(val first: Int, val second: Int? = null, val third: Int? = null) {
    override fun toString(): String {
        var s = "$first"
        if (second != null) {
            s += ", $second"
        }
        if (third != null) {
            s += ", $third"
        }
        return s
    }
}
