package com.dandobai.backend.auth

import com.dandobai.backend.user.User
import com.dandobai.backend.user.UserRepository
import jakarta.validation.Valid
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val emailPolicy: EmailPolicy
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody req: RegisterRequest): AuthResponse {

        if (userRepository.existsByEmail(req.email)) {
            throw IllegalArgumentException("Email already registered")
        }

        val role = emailPolicy.determineRole(req.email)

        val hashed = requireNotNull(passwordEncoder.encode(req.password))

        val user = User(
            email = req.email,
            password = hashed,
            role = role
        )

        userRepository.save(user)

        return AuthResponse(jwtService.generateToken(user.email))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(req.email)
            .orElseThrow { IllegalArgumentException("Invalid credentials") }

        if (!passwordEncoder.matches(req.password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        return AuthResponse(jwtService.generateToken(user.email))
    }
}

