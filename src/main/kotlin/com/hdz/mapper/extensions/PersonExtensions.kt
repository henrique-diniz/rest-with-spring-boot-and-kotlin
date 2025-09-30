package com.hdz.mapper.extensions

import com.hdz.model.Person
import com.hdz.data.vo.v1.PersonVO
import com.hdz.controller.PersonController
import org.springframework.hateoas.server.mvc.linkTo

fun Person.toVO(): PersonVO {
    return PersonVO(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        address = this.address,
        gender = this.gender
    ).add(
        linkTo<PersonController> { find(id) }.withSelfRel()
    )
}

fun List<Person>.toVO(): List<PersonVO> = this.map { it.toVO() }