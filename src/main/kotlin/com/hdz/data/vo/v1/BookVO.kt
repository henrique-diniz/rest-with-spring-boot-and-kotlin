package com.hdz.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.hateoas.RepresentationModel
import java.util.*

@JsonPropertyOrder(value = ["id", "author", "launchDate", "price", "title"])
data class BookVO(
    var id: Long = 0,
    var author: String = "",
    var launchDate: Date = Date(),
    var price: Double = 0.0,
    var title: String = ""
): RepresentationModel<PersonVO>()