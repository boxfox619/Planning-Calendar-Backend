package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.lambda.TaskCreateRequest
import com.google.gson.Gson

class AddTaskHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private val gson = Gson()

    override fun handleRequest(req: APIGatewayProxyRequestEvent, ctx: Context): APIGatewayProxyResponseEvent {
        ctx.logger.log("Create Task : $req")
        return try {
            val input = gson.fromJson<TaskCreateRequest>(req.body, TaskCreateRequest::class.java)
            input.assertFields()
            val tasks = taskRepo.createTask(input.name, input.sqlDate, input.hour, input.endHour).blockingGet()
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