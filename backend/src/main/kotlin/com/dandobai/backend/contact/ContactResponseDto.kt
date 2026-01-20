package com.dandobai.backend.contact

data class ContactResponseDto(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val imagePath: String?
)