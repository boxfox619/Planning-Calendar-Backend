package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.boxfox.calendar.model.RecordNotFoundException
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.MissingParameterError
import com.boxfox.calendar.model.lambda.TaskEditRequest
import com.google.gson.Gson

class EditTaskHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private val gson = Gson()

    override fun handleRequest(req: APIGatewayProxyRequestEvent, ctx: Context): APIGatewayProxyResponseEvent {
        ctx.logger.log("Create Task : $req")
        return try {
            val taskId = req.pathParameters.get("id")?.toInt() ?: throw MissingParameterError("id")
            val input = gson.fromJson<TaskEditRequest>(req.body, TaskEditRequest::class.java)
            taskRepo.editTask(taskId, input).blockingGet()?.let { throw it }
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