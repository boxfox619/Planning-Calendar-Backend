package com.boxfox.calendar.model

open class Task {
    var id: Int = 0
    lateinit var name: String
    lateinit var date: String
    var startHour: Short = 0
    var endHour: Short = 1

}