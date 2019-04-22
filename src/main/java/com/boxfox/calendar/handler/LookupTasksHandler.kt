package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.model.lambda.TaskLookupRequest
import com.boxfox.calendar.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase

class LookupTasksHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<TaskLookupRequest, Response> {
    override fun handleRequest(input: TaskLookupRequest, ctx: Context): Response {
        ctx.logger.log("Task lookup : $input")
        return try {
            input.assertFields()
            val tasks = taskRepo.loadTasks(input.year, input.month).blockingGet()
            Response(200, tasks)
        } catch (e: Throwable) {
            ctx.logger.log(e.message)
            when (e) {
                is AssertionError -> Response(400, e.message ?: "missing parameter")
                else -> Response(500, "internal server error")
            }
        }
    }

}