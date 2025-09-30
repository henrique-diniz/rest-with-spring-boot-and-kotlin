package com.hdz.data.vo.v1

import jakarta.xml.bind.annotation.XmlRootElement

data class AccountCredentialsVO(
    var username: String? = null,
    var password: String? = null
)