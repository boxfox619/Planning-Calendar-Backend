package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.lambda.TaskRequest
import com.boxfox.calendar.util.TestUtil.anyObject
import com.boxfox.calendar.util.TestUtil.mockContext
import com.google.gson.Gson
import io.reactivex.Completable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class EditTaskHandlerTest {
    private val gson = Gson()

    @Test
    fun editTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val task = TaskRequest().apply {
            this.name = "이름"
            this.date = "2019-11-23"
            this.startHour = 12
            this.endHour = 14
        }
        `when`(taskRepoMock.editTask(anyInt(), anyObject())).thenReturn(Completable.complete())
        val requestMock = Mockito.mock(APIGatewayProxyRequestEvent::class.java)
        `when`(requestMock.headers).thenReturn(mapOf("origin" to "test-origin"))
        `when`(requestMock.pathParameters).thenReturn(mapOf("id" to "123"))
        `when`(requestMock.body).thenReturn(gson.toJson(task))
        val handler = EditTaskHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 201)
    }

    @Test
    fun assertionTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val requestMock = Mockito.mock(APIGatewayProxyRequestEvent::class.java)
        val handler = EditTaskHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 400)
    }

}