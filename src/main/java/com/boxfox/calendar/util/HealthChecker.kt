package com.boxfox.calendar.util

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.repository.postgres.TaskRepository
import java.time.Duration
import java.time.Instant

object HealthChecker {
    private val taskRepo = TaskRepository()

    fun pipe(req: APIGatewayProxyRequestEvent?, ctx: Context?, handler: () -> Response): Response {
        return if (req == null || ctx == null || req.headers == null) {
            ctx?.logger?.log("task handler health check")
            val throwable = taskRepo.ping().blockingGet()
            throwable?.let { ctx?.logger?.log(throwable.message) }
            ctx?.logger?.log("database ping test")
            Response(200, "pong")
        } else {
            val startTime = Instant.now()
            val response = handler()
            val endTime = Instant.now()
            val duration = Duration.between(startTime, endTime)
            ctx.logger.log("spend ${duration.seconds} sec")
            response
        }
    }
}