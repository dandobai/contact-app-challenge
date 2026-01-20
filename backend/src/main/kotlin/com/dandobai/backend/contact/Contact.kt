package com.dandobai.backend.contact

import com.dandobai.backend.user.User
import jakarta.persistence.*

@Entity
@Table(name = "contacts")
data class Contact(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val phone: String,

    val imagePath: String? = null,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)
