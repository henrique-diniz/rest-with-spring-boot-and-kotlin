package com.hdz.services

import com.hdz.controller.BookController
import com.hdz.data.vo.v1.BookVO
import com.hdz.exceptions.RequiredObjectIsNullException
import com.hdz.mapper.ModelMapper
import com.hdz.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger
import com.hdz.exceptions.ResourceNotFoundException
import com.hdz.model.Book
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.data.domain.Pageable

@Service
class BookService {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<BookVO>

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<BookVO>> {
        logger.info("Finding all books")
        val books = bookRepository.findAll(pageable)
        val vos = books.map { b -> ModelMapper.parseObject(b, BookVO::class.java) }
        vos.map { p -> p.add(linkTo(BookController::class.java).slash(p.id).withSelfRel()) }
        return assembler.toModel(vos)
    }

    fun findById(id: Long): BookVO {
        logger.info("Finding one book by id $id")
        val book = bookRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Book not found") }
        val bookVo = ModelMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(book.id).withSelfRel()
        bookVo.add(withSelfRel)
        return bookVo
    }

    fun create(book: BookVO?): BookVO {
        if (book == null) throw RequiredObjectIsNullException()

        logger.info("Creating book ${book.title}")
        val entity: Book = ModelMapper.parseObject(book, Book::class.java)
        val bookVo = ModelMapper.parseObject(bookRepository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVo.id).withSelfRel()
        bookVo.add(withSelfRel)
        return bookVo
    }

    fun update(book: BookVO?): BookVO {
        if (book == null ) throw RequiredObjectIsNullException()

        logger.info("Updating book ${book.title}")
        val entity = bookRepository.findById(book.id).orElseThrow { ResourceNotFoundException("Book not found") }

        entity.author = book.author
        entity.launchDate = book.launchDate
        entity.price = book.price
        entity.title = book.title

        bookRepository.save(entity)

        val bookVo = ModelMapper.parseObject(bookRepository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVo.id).withSelfRel()
        bookVo.add(withSelfRel)
        return bookVo
    }

    fun delete(id: Long) {
        logger.info("Deleting book with id $id")
        val entity = bookRepository.findById(id).orElseThrow { ResourceNotFoundException("Book not found") }
        bookRepository.deleteById(entity.id)
    }
}
