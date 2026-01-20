package com.dandobai.backend.contact

import com.dandobai.backend.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContactService(
    private val contactRepository: ContactRepository,
    private val userService: UserService
) {

    @Transactional
    fun create(dto: ContactDto): Contact {
        val user = userService.currentUser()
        val contact = Contact(
            name = dto.name,
            email = dto.email,
            phone = dto.phone,
            user = user
        )
        return contactRepository.save(contact)
    }

    fun listForCurrentUser(): List<Contact> {
        val user = userService.currentUser()
        return contactRepository.findAllByUser(user)
    }

    fun getByIdForCurrentUser(id: Long): Contact {
        val user = userService.currentUser()
        val contact = contactRepository.findById(id)
            .orElseThrow { ContactNotFoundException() }

        if (contact.user.id != user.id) {
            throw IllegalStateException("You do not own this contact")
        }

        return contact
    }

    @Transactional
    fun update(id: Long, dto: ContactDto): Contact {
        val contact = getByIdForCurrentUser(id)
        val updated = contact.copy(
            name = dto.name,
            email = dto.email,
            phone = dto.phone
        )
        return contactRepository.save(updated)
    }

    @Transactional
    fun delete(id: Long) {
        val contact = getByIdForCurrentUser(id)
        contactRepository.delete(contact)
    }
}
fun Contact.toDto() = ContactResponseDto(
    id = this.id!!,
    name = this.name,
    email = this.email,
    phone = this.phone,
    imagePath = this.imagePath
)
