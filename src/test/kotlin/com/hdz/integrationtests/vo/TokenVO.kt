package com.hdz.integrationtests.vo

import jakarta.xml.bind.annotation.XmlRootElement
import java.util.*

@XmlRootElement(name = "TokenVO")
class TokenVO(
    var username: String? = null,
    var authenticated: Boolean? = null,
    var created: Date? = null,
    var expiration: Date? = null,
    var refreshToken: String? = null,
    var accessToken: String? = null,
)