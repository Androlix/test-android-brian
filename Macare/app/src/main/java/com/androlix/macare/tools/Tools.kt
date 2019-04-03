package com.androlix.macare.tools

import com.androlix.macare.data.GlycemicLevelEntry

object Tools {

    fun formatDateTimeString(entry: GlycemicLevelEntry): String {
        return "${entry.day} / ${entry.month} / ${entry.year}  -  ${entry.hour}:${entry.minutes} "
    }

    fun formatDateString(entry: GlycemicLevelEntry): String {
        return "Date: ${entry.day} / ${entry.month} / ${entry.year}"
    }

    fun formatTimeString(entry: GlycemicLevelEntry): String {
        return "Time: ${entry.hour}:${entry.minutes} "
    }
}