package com.boxfox.calendar.data.model.lambda

class DeleteTaskRequest(val id: Int) : Request {

    override fun assertFields() {
        assertTrue(id > 0, "invalid parameter : id")
    }

}