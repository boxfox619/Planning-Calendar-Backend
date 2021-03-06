package com.boxfox.calendar.model.lambda

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.google.gson.Gson

class Response() : APIGatewayProxyResponseEvent() {

    constructor(statusCode: Int, bodyData: Any, origin: String? = "*") : this() {
        val headers = mutableMapOf(
                "Content-Type" to "application/json",
                "Access-Control-Allow-Origin" to (origin ?: "*"),
                "Access-Control-Allow-Headers" to "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
                "Access-Control-Allow-Methods" to "OPTIONS,POST,GET,DELETE,PUT",
                "Access-Control-Allow-Credentials" to "true")
        val bodyString = when (bodyData) {
            String -> {
                headers.put("Content-Type", "application/text")
                bodyData.toString()
            }
            else -> Gson().toJson(bodyData)
        }
        super.setStatusCode(statusCode)
        super.setBody(bodyString)
        super.setHeaders(headers)
    }
}