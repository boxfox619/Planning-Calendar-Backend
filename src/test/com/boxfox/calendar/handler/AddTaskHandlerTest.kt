package com.boxfox.calendar.handler

import com.boxfox.calendar.data.model.Task
import com.boxfox.calendar.data.model.lambda.TaskCreateRequest
import com.boxfox.calendar.data.model.lambda.TaskLookupRequest
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.util.ConstextMock.createContext
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*


class AddTaskHandlerTest {

    @Test
    fun lookupTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        `when`(taskRepoMock.createTask(Matchers.any(Task::class.java))).thenReturn(Single.just(Task(1)))
        val request = TaskCreateRequest(1, 1)
        val handler = AddTaskHandler(taskRepoMock)
        val response = handler.handleRequest(request, createContext())
        assertEquals(response.statuscode, 200)
    }

    @Test
    fun assertionTest() {
        val taskRepoMock = mock(TaskUsecase::class.java)
        val request = TaskCreateRequest(1, 0)
        val handler = AddTaskHandler(taskRepoMock)
        val response = handler.handleRequest(request, createContext())
        assertEquals(response.statuscode, 400)
    }

}