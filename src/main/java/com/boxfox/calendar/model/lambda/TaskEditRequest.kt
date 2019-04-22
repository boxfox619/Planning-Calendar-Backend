package com.boxfox.calendar.model.lambda

import com.boxfox.calendar.model.Task

class TaskEditRequest(id: Int) : Task(id), Request {

    override fun assertFields() {
        valid(name, "missing parameter : name")
        valid(date, "missing parameter : date")
        assertTrue(hour in 1..24, "invalid parameter : hour")
    }
}