package com.app.nytimesdemo.data.enums


enum class Period {
    DAY, WEEK, MONTH;

    fun getPeriod(): Int {
        return when (this) {
            DAY -> 1
            WEEK -> 7
            MONTH -> 30
            else -> -1
        }
    }
}