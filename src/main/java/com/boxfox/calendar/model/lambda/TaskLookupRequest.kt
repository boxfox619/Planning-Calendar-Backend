package com.boxfox.calendar.model.lambda

class TaskLookupRequest : Request {
    var year: Int = 0
    var month: Int = 0

    override fun assertFields() {
        assertTrue(year > 0, "Invalid parameter: year")
        assertTrue(month in 1..12, "Invalid parameter: month")
    }

}