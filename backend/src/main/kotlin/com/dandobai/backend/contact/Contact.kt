package com.dandobai.backend.contact

import jakarta.persistence.Entity
import jakarta.persistence.GenerationType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column

import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "contacts")
data class Contact(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val phone: String,

    val imagePath: String? = null
)
