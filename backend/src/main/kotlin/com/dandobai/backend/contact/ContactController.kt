package com.dandobai.backend.contact

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/contacts")
class ContactController (
    private val service: ContactService
) {
    @GetMapping
    fun getAll() =
        service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        service.findById(id)

    @PostMapping
    fun create(@Valid
               @RequestBody dto: ContactDto) =
        service.create(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long,
               @Valid
               @RequestBody dto: ContactDto) =
        service.update(id,dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long){
        service.delete(id)
    }

    @PostMapping("/{id}/image")
    fun uploadImage(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile) =
        service.uploadImage(id, file)
}