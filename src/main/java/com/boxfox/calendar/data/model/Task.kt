package com.boxfox.calendar.data.model

import java.util.*

open class Task(val id: Int) {
    lateinit var name: String
    lateinit var date: Date
    var hour: Short = 1

    val sqlDate: java.sql.Date
        get() = java.sql.Date(date.time)

}