package com.boxfox.calendar.util

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import org.mockito.Matchers
import org.mockito.Mockito

object ConstextMock {
    fun createContext(): Context {
        val contextMock = Mockito.mock(Context::class.java)
        val loggerMock = Mockito.mock(LambdaLogger::class.java)
        Mockito.`when`(contextMock.logger).thenReturn(loggerMock)
        Mockito.doAnswer { invocation ->
            println(invocation.arguments[0])
            null
        }.`when`<LambdaLogger>(loggerMock).log(Matchers.anyString())
        return contextMock
    }
}