package com.dandobai.backend.contact

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/contacts")
class ContactController(
    private val contactService: ContactService
) {

    @GetMapping
    fun list(): List<ContactResponseDto> =
        contactService.listForCurrentUser().map { it.toDto() }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ContactResponseDto =
        contactService.getByIdForCurrentUser(id).toDto()

    @PostMapping
    fun create(@Valid @RequestBody dto: ContactDto): ContactResponseDto =
        contactService.create(dto).toDto()

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody dto: ContactDto): ContactResponseDto =
        contactService.update(id, dto).toDto()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) =
        contactService.delete(id)
}

