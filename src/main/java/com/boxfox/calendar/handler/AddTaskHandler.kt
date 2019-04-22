package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.model.lambda.TaskCreateRequest
import com.boxfox.calendar.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase

class AddTaskHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<TaskCreateRequest, Response> {
    override fun handleRequest(input: TaskCreateRequest, ctx: Context): Response {
        ctx.logger.log("Create Task : $input")
        return try {
            input.assertFields()
            val task = taskRepo.createTask(input).blockingGet()
            Response(200, task)
        } catch (e: Throwable) {
            ctx.logger.log(e.message)
            when (e) {
                is AssertionError -> Response(400, e.message ?: "missing parameter")
                else -> Response(500, "internal server error")
            }
        }
    }

}