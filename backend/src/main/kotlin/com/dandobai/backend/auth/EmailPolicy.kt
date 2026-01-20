package com.dandobai.backend.auth

import com.dandobai.backend.user.Role
import org.springframework.stereotype.Component

@Component
class EmailPolicy {

    private val adminEmail = "daniel.dobai@valami.hu"
    private val allowedDomain = "@uxstudios.com"

    fun determineRole(email: String): Role {
        return when {
            email.equals(adminEmail, ignoreCase = true) -> Role.ADMIN
            email.endsWith(allowedDomain, ignoreCase = true) -> Role.USER
            else -> throw IllegalArgumentException("Registration is restricted to UX Studios members")
        }
    }
}
