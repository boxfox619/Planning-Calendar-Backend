package com.boxfox.calendar.data.model.lambda

import com.boxfox.calendar.data.model.Task

class TaskCreateRequest(id: Int, val endHour: Int) : Task(id), Request {

    override fun assertFields() {
        valid(name, "missing parameter : name")
        valid(date, "missing parameter date")
        assertTrue(hour in 1..24, "invalid parameter : hour")
        assertTrue(endHour > 0, "invalid paramter : endHour")
    }
}