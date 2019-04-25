package com.boxfox.calendar.model.lambda

import com.boxfox.calendar.model.Task

class TaskRequest : Task(), Request {

    override fun assertFields() {
        valid(name, "missing parameter : name")
        valid(date, "missing parameter date")
        assertTrue(startHour in 0..23, "invalid parameter : hour")
        assertTrue(endHour > startHour, "invalid paramter : endHour")
    }
}