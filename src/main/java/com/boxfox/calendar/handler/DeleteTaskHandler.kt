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
import com.boxfox.calendar.util.HealthChecker

class DeleteTaskHandler(private val taskRepo: TaskUsecase = TaskRepository()) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(req: APIGatewayProxyRequestEvent, ctx: Context) = HealthChecker.pipe(req, ctx) {
        val origin = req.headers["origin"]
        ctx.logger.log("Delete Task : $req")
        ctx.logger.log("Origin : $origin")
        try {
            val taskId = req.pathParameters.get("id")?.toInt() ?: throw MissingParameterError("id")
            taskRepo.removeTask(taskId).blockingGet()?.let { throw it }
            Response(201, "success", origin)
        } catch (e: Throwable) {
            ctx.logger.log(e.message)
            when (e) {
                is AssertionError -> Response(400, e.message ?: "missing parameter", origin)
                is RecordNotFoundException -> Response(409, e.message!!, origin)
                else -> Response(500, "internal server error", origin)
            }
        }
    }

}