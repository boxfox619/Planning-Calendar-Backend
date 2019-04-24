package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.lambda.TaskRequest
import com.google.gson.Gson

class AddTaskHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private val gson = Gson()

    override fun handleRequest(req: APIGatewayProxyRequestEvent, ctx: Context): APIGatewayProxyResponseEvent {
        val origin = req.headers["origin"]
        ctx.logger.log("Create Task : $req")
        ctx.logger.log("Origin : $origin")
        return try {
            val input = gson.fromJson<TaskRequest>(req.body, TaskRequest::class.java).also { it.assertFields() }
            val tasks = taskRepo.createTask(input).blockingGet()
            Response(200, tasks, origin)
        } catch (e: Throwable) {
            ctx.logger.log(e.message)
            when (e) {
                is AssertionError -> Response(400, e.message ?: "missing parameter", origin)
                else -> Response(500, "internal server error", origin)
            }
        }
    }

}