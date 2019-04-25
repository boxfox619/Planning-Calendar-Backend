package com.boxfox.calendar.util

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.boxfox.calendar.model.lambda.Response

object HealthChecker {
    fun pipe(req: APIGatewayProxyRequestEvent?, ctx: Context?, handler: () -> Response): Response {
        return if (req == null || ctx == null || req.headers == null) {
            ctx?.logger?.log("task handler health check")
            Response(200, "pong")
        } else {
            handler()
        }
    }
}