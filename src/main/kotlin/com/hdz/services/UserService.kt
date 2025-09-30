package com.hdz.services

import com.hdz.controller.BookController
import com.hdz.data.vo.v1.BookVO
import com.hdz.exceptions.RequiredObjectIsNullException
import com.hdz.mapper.ModelMapper
import com.hdz.mapper.custom.BookMapper
import com.hdz.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger
import com.hdz.exceptions.ResourceNotFoundException
import com.hdz.model.Book
import com.hdz.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class UserService(@field:Autowired var repository: UserRepository) : UserDetailsService{

    private val logger = Logger.getLogger(UserService::class.java.name)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Finding one User by Username $username!")
        val user = repository.findByUsername(username)
        return user ?: throw UsernameNotFoundException("Username $username not found!")
    }
}