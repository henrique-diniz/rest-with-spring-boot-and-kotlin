package com.hdz.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "books")
data class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "author", nullable = false, length = 80)
    var author: String = "",

    @Column(name = "launch_date", nullable = false)
    var launchDate: Date,

    @Column(name = "price", nullable = false, length = 65)
    var price: Double = 0.0,

    @Column(name = "title", nullable = false, length = 80)
    var title: String = ""
) {
    constructor() : this(0L, "", Date(), 0.0, "") {
        this.launchDate = Date()
    }
}