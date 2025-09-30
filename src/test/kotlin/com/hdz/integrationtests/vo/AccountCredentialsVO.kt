package com.hdz.integrationtests.vo
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "AccountCredentialsVO")
data class AccountCredentialsVO(
    var username: String? = null,
    var password: String? = null
)