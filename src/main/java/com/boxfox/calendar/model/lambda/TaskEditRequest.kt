package com.boxfox.calendar.model.lambda

import com.boxfox.calendar.model.Task

class TaskEditRequest : Task(), Request {

    override fun assertFields() {
        assertTrue(id > 0, "missing parameter : id")
        valid(name, "missing parameter : name")
        valid(date, "missing parameter : date")
        assertTrue(hour in 1..24, "invalid parameter : hour")
    }
}