package com.boxfox.calendar.handler

import com.boxfox.calendar.data.model.Task
import com.boxfox.calendar.data.model.lambda.TaskLookupRequest
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.util.ConstextMock.createContext
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*


class LookupTaskHandlerTest {

    @Test
    fun lookupTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        `when`(taskRepoMock.loadTasks(Matchers.anyInt(), Matchers.anyInt())).thenReturn(Single.just(listOf(Task(1))))
        val request = TaskLookupRequest(2019, 12)
        val handler = LookupTasksHandler(taskRepoMock)
        val response = handler.handleRequest(request, createContext())
        assertEquals(response.statuscode, 200)
    }

    @Test
    fun assertionTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val request = TaskLookupRequest(0, 12)
        val handler = LookupTasksHandler(taskRepoMock)
        val response = handler.handleRequest(request, createContext())
        assertEquals(response.statuscode, 400)
    }

}