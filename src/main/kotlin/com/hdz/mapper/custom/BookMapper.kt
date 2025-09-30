package com.hdz.mapper.custom

import com.hdz.data.vo.v1.BookVO
import com.hdz.model.Book
import org.springframework.stereotype.Service

@Service
class BookMapper {
    fun mapEntityToVO(book: Book): BookVO {
        val vo = BookVO()
        vo.id = book.id
        vo.author = book.author
        vo.launchDate = book.launchDate
        vo.price = book.price
        vo.title = book.title
        return vo
    }

    fun mapVOToEntity(book: BookVO): Book {
        val entity = Book()
        entity.id = book.id
        entity.author = book.author
        entity.launchDate = book.launchDate
        entity.price = book.price
        entity.title = book.title
        return entity
    }
}