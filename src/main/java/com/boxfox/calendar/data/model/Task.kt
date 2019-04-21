package com.boxfox.calendar.data.model

class Task(val id: Int) : Model() {
    lateinit var name: String
    lateinit var date: String
    var durationTime: Int = 1

    override fun assertFields() {
        valid(name, "missing parameter : name")
        valid(date, "missing parameter date")
    }

}