package com.hdz.integrationtests.controller.withjson

import com.hdz.integrationtests.TestConfigs
import com.hdz.integrationtests.vo.AccountCredentialsVO
import com.hdz.integrationtests.vo.TokenVO
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import com.hdz.integrationtests.testcontainers.AbstractIntegrationTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerJsonTest : AbstractIntegrationTest() {

    private lateinit var tokenVo: TokenVO

    @BeforeAll
    fun setupTests() {
        tokenVo = TokenVO()
    }

    @Test
    @Order(0)
    fun testLogin() {
        val user = AccountCredentialsVO("hdiniz", "admin123")

        tokenVo = given().basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(user)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)

        assertNotNull(tokenVo.accessToken)
        assertNotNull(tokenVo.refreshToken)
    }

    @Test
    @Order(1)
    fun testRefresh() {
        val token = given().basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", tokenVo.username)
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer ${tokenVo.refreshToken}")
            .`when`()
            .put("{username}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)

        assertNotNull(token.accessToken)
        assertNotNull(token.refreshToken)
    }
}