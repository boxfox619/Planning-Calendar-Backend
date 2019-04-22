package com.boxfox.calendar.model.lambda

import com.boxfox.calendar.model.Task

class TaskCreateRequest: Task(), Request {
    var endHour: Short = 0

    override fun assertFields() {
        valid(name, "missing parameter : name")
        valid(date, "missing parameter date")
        assertTrue(hour in 1..24, "invalid parameter : hour")
        assertTrue(endHour > hour, "invalid paramter : endHour")
    }
}