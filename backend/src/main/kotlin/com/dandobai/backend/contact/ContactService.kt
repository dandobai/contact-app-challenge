package com.dandobai.backend.contact
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class ContactService (
    private val repository: ContactRepository
){
    private val log = KotlinLogging.logger {}

    fun findAll(): List<Contact> {
        log.info { "Fetching all contacts" }
        return repository.findAll()
    }

    fun findById(id: Long): Contact {
        log.info { "Fetching contact with id=$id" }
        return repository.findById(id)
            .orElseThrow{ ContactNotFoundException(id) }
    }

    fun create(dto: ContactDto): Contact {
        log.info { "Creating new contact: $dto" }
        val entity = dto.toEntity()
        return repository.save(entity)
    }

    fun update(id: Long, dto: ContactDto): Contact {
        log.info { "Updating contact with id=$id" }
        val existing = findById(id)
        val updated = existing.copy(
            name = dto.name,
            email = dto.email,
            phone = dto.phone
        )
        return repository.save(updated)
    }

    fun delete(id: Long) {
        log.info { "Deleting contact with id=$id" }
        repository.deleteById(id)
    }

    fun uploadImage(id: Long, file: MultipartFile): Contact {
        log.info { "Uploading image for contact id=$id" }

        val contact = findById(id)

        val uploadsDir = Paths.get("uploads").toAbsolutePath()
        if(!Files.exists(uploadsDir)) {
            Files.createDirectories(uploadsDir)
        }

        val suffix = UUID.randomUUID().toString().take(8)
        val filename = "contact_${id}_$suffix"
        val filePath = uploadsDir.resolve(filename)

        Files.write(filePath, file.bytes)

        val updated = contact.copy(imagePath = filePath.toString())
        return repository.save(updated)
    }
}