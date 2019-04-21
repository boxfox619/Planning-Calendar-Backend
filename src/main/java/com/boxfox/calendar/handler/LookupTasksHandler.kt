package com.boxfox.calendar.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.boxfox.calendar.data.model.Response
import com.boxfox.calendar.data.model.Task

class LookupTasksHandler : RequestHandler<Task, Response> {
    override fun handleRequest(input: Task, ctx: Context): Response {
        ctx.logger.log("Login : $input")
        try {
            input.assertFields()

        } catch (e: AssertionError) {
            return Response(400, e.message ?: "missing parameter")
        }
        return Response(201, "input at lambda function is empty")
    }

}