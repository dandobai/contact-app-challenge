package com.dandobai.backend.contact

import com.dandobai.backend.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ContactRepository : JpaRepository<Contact, Long> {

    fun findAllByUser(user: User): List<Contact>

    fun findByIdAndUser(id: Long, user: User): Optional<Contact>
}
