package com.dandobai.backend.user

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun currentUser(): User {
        val auth = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authenticated user")

        val email = auth.name
        return userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("User not found") }
    }
}
