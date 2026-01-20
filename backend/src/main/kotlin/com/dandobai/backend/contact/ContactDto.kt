package com.dandobai.backend.contact

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ContactDto(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Phone is required")
    val phone: String
)
