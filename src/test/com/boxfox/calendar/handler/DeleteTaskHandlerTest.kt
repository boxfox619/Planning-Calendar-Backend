package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.util.TestUtil.mockContext
import com.google.gson.Gson
import io.reactivex.Completable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class DeleteTaskHandlerTest {
    private val gson = Gson()

    @Test
    fun deleteTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        `when`(taskRepoMock.removeTask(anyInt())).thenReturn(Completable.complete())
        val requestMock = Mockito.mock(APIGatewayProxyRequestEvent::class.java)
        `when`(requestMock.headers).thenReturn(mapOf("origin" to "test-origin"))
        `when`(requestMock.pathParameters).thenReturn(mapOf("id" to "123"))
        val handler = DeleteTaskHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 201)
    }

    @Test
    fun assertionTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val requestMock = Mockito.mock(APIGatewayProxyRequestEvent::class.java)
        val handler = DeleteTaskHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 400)
    }

}