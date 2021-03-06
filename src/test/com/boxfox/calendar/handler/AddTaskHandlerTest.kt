package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.lambda.TaskRequest
import com.boxfox.calendar.util.TestUtil
import com.boxfox.calendar.util.TestUtil.mockContext
import com.google.gson.Gson
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class AddTaskHandlerTest {
    private val gson = Gson()

    @Test
    fun createTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val task = TaskRequest().apply {
            this.name = "이름"
            this.date = "2019-11-23"
            this.startHour = 12
            this.endHour = 14
        }
        `when`(taskRepoMock.createTask(TestUtil.anyObject())).thenReturn(Single.just(task))
        val requestMock = mock(APIGatewayProxyRequestEvent::class.java)
        `when`(requestMock.headers).thenReturn(mapOf("origin" to "test-origin"))
        `when`(requestMock.body).thenReturn(gson.toJson(task))
        val handler = AddTaskHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.body, gson.toJson(task))
        assertEquals(response.statusCode, 200)
    }

    @Test
    fun assertionTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val task = TaskRequest().apply {
            this.date = "2019-11-23"
            this.startHour = 12
            this.endHour = 14
        }
        val requestMock = mock(APIGatewayProxyRequestEvent::class.java)
        `when`(requestMock.headers).thenReturn(mapOf("origin" to "test-origin"))
        `when`(requestMock.body).thenReturn(gson.toJson(task))
        val handler = AddTaskHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 400)
    }

}