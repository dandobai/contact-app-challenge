package com.dandobai.backend.contact

import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository : JpaRepository<Contact, Long> {
}