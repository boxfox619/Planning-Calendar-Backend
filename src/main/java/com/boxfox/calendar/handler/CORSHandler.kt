package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.boxfox.calendar.model.lambda.Response
import com.boxfox.calendar.util.HealthChecker

class CORSHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(req: APIGatewayProxyRequestEvent, ctx: Context) = HealthChecker.pipe(req, ctx) {
        val origin = req.headers["origin"]
        ctx.logger.log("CORS Options : $req")
        ctx.logger.log("Origin : $origin")
        Response(201, "", origin)
    }

}