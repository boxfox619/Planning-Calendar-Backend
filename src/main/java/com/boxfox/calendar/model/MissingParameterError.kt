package com.boxfox.calendar.model

import java.lang.AssertionError

class MissingParameterError(paramName: String): AssertionError("missing parameter : $paramName")