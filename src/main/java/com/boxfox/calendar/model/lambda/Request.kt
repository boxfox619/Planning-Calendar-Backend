package com.boxfox.calendar.model.lambda

interface Request {

    @Throws(AssertionError::class)
    fun assertFields()
}

fun valid(str: String?, msg: String) {
    if (str == null || str.isEmpty()) {
        throw AssertionError(msg)
    }
}

fun valid(obj: Any?, msg: String) {
    if (obj == null) {
        throw AssertionError(msg)
    }
}

fun assertTrue(check: Boolean, msg: String) {
    if (!check) {
        throw AssertionError(msg)
    }
}