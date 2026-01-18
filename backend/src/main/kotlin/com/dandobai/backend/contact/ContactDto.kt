package com.dandobai.backend.contact

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ContactDto(
    @field:NotBlank
    val name: String,

    @field:Email
    val email: String,

    @field:NotBlank
    val phone: String
) {
    fun toEntity() = Contact(
        name = name,
        email = email,
        phone = phone
    )
}