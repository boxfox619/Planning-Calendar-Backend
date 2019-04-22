package com.boxfox.calendar.model.lambda

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.google.gson.Gson

class Response() : APIGatewayProxyResponseEvent() {

    constructor(statusCode: Int, bodyData: Any) : this() {
        val headers = mapOf("Content-Type" to "application/json", "Access-Control-Allow-Origin" to "*")
        val bodyString = when (bodyData) {
            String -> bodyData.toString()
            else -> Gson().toJson(bodyData)
        }
        super.setStatusCode(statusCode)
        super.setBody(bodyString)
        super.setHeaders(headers)
    }
}