package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.lambda.TaskRequest
import com.boxfox.calendar.util.TestUtil.mockContext
import com.google.gson.Gson
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*


class LookupTaskHandlerTest {
    private val gson = Gson()

    @Test
    fun lookupTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val task = TaskRequest().apply {
            this.name = "이름"
            this.date = "2019-11-23"
            this.startHour = 12
            this.endHour = 14
        }
        `when`(taskRepoMock.loadTasks(anyInt(), anyInt())).thenReturn(Single.just(listOf(task)))
        val requestMock = Mockito.mock(APIGatewayProxyRequestEvent::class.java)
        `when`(requestMock.headers).thenReturn(mapOf("origin" to "test-origin"))
        `when`(requestMock.queryStringParameters).thenReturn(mapOf("year" to "2019", "month" to "12"))
        val handler = LookupTasksHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 200)
    }

    @Test
    fun assertionTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val requestMock = Mockito.mock(APIGatewayProxyRequestEvent::class.java)
        val handler = LookupTasksHandler(taskRepoMock)
        val response = handler.handleRequest(requestMock, mockContext())
        assertEquals(response.statusCode, 400)
    }

}