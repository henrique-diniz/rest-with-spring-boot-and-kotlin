package com.hdz.services

import com.hdz.controller.PersonController
import com.hdz.data.vo.v1.PersonVO
import com.hdz.exceptions.RequiredObjectIsNullException
import com.hdz.data.vo.v2.PersonVO as PersonVOV2
import com.hdz.exceptions.ResourceNotFoundException
import com.hdz.mapper.ModelMapper
import com.hdz.mapper.custom.PersonMapper
import com.hdz.model.Person
import com.hdz.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonVO>

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("Finding all persons")
        val persons =  repository.findAll(pageable)
        val vos = persons.map { p -> ModelMapper.parseObject(p, PersonVO::class.java) }
        vos.map { p -> p.add(linkTo(PersonController::class.java).slash(p.id).withSelfRel()) }
        return assembler.toModel(vos)
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person by id $id")

        var person =  repository.findById(id)
            .orElseThrow({ResourceNotFoundException("Person not found")})
        val personVo = ModelMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVo.id).withSelfRel()
            .withSelfRel()
        personVo.add(withSelfRel)
        return personVo
    }

    fun create(person: PersonVO?): PersonVO {

        if(person == null) throw RequiredObjectIsNullException()

        logger.info("Creating person ${person.firstName}")
        var entity: Person = ModelMapper.parseObject(person, Person::class.java)
        val personVo =  ModelMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVo.id).withSelfRel()
            .withSelfRel()
        personVo.add(withSelfRel)
        return personVo
    }

    fun createV2(person: PersonVOV2): PersonVOV2 {
        logger.info("Creating person ${person.firstName}")
        var entity: Person = mapper.mapVOToEntity(person)
        return mapper.mapEntityToVO(repository.save(entity))
    }

    fun update(person: PersonVO?): PersonVO {

        if(person == null) throw RequiredObjectIsNullException()

        logger.info("Updating person with id ${person.id}")
        val entity = repository.findById(person.id)
            .orElseThrow({ResourceNotFoundException("Person not found")})

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        repository.save(entity)

        val personVo =  ModelMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVo.id).withSelfRel()
        personVo.add(withSelfRel)
        return personVo
    }

    @Transactional
    fun disablePerson(id: Long): PersonVO {
        logger.info("Disabling person with id $id")
        repository.disablePerson(id)

        val person = repository.findById(id)
            .orElseThrow({ResourceNotFoundException("Person not found")})

        repository.disablePerson(id)

        val personVo = ModelMapper.parseObject(person, PersonVO::class.java)
        personVo.enabled = false
        val withSelfRel = linkTo(PersonController::class.java).slash(personVo.id).withSelfRel()
        personVo.add(withSelfRel)
        return personVo
    }

    fun delete(id: Long) {
        logger.info("Deleting person with id $id")
        val person = repository.findById(id)
            .orElseThrow({ResourceNotFoundException("Person not found")})
        repository.deleteById(person.id)
    }

}