package com.boxfox.calendar.model.lambda

class TaskLookupRequest(val year: Int, val month: Int) : Request {

    override fun assertFields() {
        assertTrue(year > 0, "Invalid parameter: year")
        assertTrue(month in 1..12, "Invalid parameter: month")
    }

}