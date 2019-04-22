package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.boxfox.calendar.data.model.RecordNotFoundException
import com.boxfox.calendar.data.model.lambda.TaskEditRequest
import com.boxfox.calendar.data.model.lambda.Response
import com.boxfox.calendar.data.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase

class DeleteTaskHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<TaskEditRequest, Response> {
    override fun handleRequest(input: TaskEditRequest, ctx: Context): Response {
        ctx.logger.log("Delete Task : $input")
        return try {
            input.assertFields()
            taskRepo.removeTask(input.id).blockingGet()?.let { throw it }
            Response(200, "success")
        } catch (e: Throwable) {
            ctx.logger.log(e.message)
            when (e) {
                is AssertionError -> Response(400, e.message ?: "missing parameter")
                is RecordNotFoundException -> Response(409, e.message!!)
                else -> Response(500, "internal server error")
            }
        }
    }

}