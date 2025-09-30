package com.hdz.data.vo.v1


import java.util.*

class TokenVO(
    var username: String? = null,
    var authenticated: Boolean? = null,
    var created: Date? = null,
    var expiration: Date? = null,
    var refreshToken: String? = null,
    var accessToken: String? = null,
)