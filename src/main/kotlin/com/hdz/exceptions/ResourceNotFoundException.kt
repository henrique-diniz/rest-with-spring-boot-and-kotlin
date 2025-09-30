package com.hdz.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(exception: String?): RuntimeException (exception) {
}