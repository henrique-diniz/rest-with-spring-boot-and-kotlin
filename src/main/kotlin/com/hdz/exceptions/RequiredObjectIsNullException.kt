package com.hdz.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException: RuntimeException {
    constructor(exception: String?): super(exception)
    constructor() : super("It is not allowed to persist a null object!")
    constructor(exception: String?, message: String?) : super(exception, Throwable(message))

}