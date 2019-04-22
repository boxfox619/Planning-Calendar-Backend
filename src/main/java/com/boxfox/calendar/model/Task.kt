package com.boxfox.calendar.model

import jdk.nashorn.internal.ir.annotations.Ignore
import java.util.*

open class Task {
    var id: Int = 0
    lateinit var name: String
    lateinit var date: Date
    var hour: Short = 1

    @Ignore
    val sqlDate: java.sql.Date
        get() = java.sql.Date(date.time)

}