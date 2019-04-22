package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.repository.postgres.TaskRepository
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.model.MissingParameterError
import com.boxfox.calendar.model.lambda.assertTrue

class LookupTasksHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(req: APIGatewayProxyRequestEvent, ctx: Context): APIGatewayProxyResponseEvent {
        ctx.logger.log("Task lookup : $req")
        return try {
            val year = req.queryStringParameters.get("year")?.toInt() ?: throw MissingParameterError("year")
            val month = req.queryStringParameters.get("month")?.toInt() ?: throw MissingParameterError("month")
            assertTrue(year > 0, "invalid parameter")
            assertTrue(month in 1..12, "invalid parameter")
            val tasks = taskRepo.loadTasks(year, month).blockingGet()
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