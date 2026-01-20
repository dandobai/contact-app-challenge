package com.dandobai.backend.image

import com.dandobai.backend.contact.ContactRepository
import com.dandobai.backend.user.UserRepository
import com.dandobai.backend.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val imageService: ImageService,
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val contactRepository: ContactRepository
) {

    @PostMapping("/user")
    fun uploadUserImage(@RequestParam("file") file: MultipartFile): String {
        val user = userService.currentUser()
        val path = imageService.saveUserImage(user.id!!, file)

        val updated = user.copy(imagePath = path)
        userRepository.save(updated)

        return path
    }

    @GetMapping("/user/image")
    fun getUserImage(): ResponseEntity<Map<String, String?>> {
        return try {
            val user = userService.currentUser()
            // Map-ként küldjük vissza, így biztosan érvényes JSON lesz: {"imagePath": "..."}
            ResponseEntity.ok(mapOf("imagePath" to user.imagePath))
        } catch (e: Exception) {
            // Így a konzolon látni fogod a pontos hiba okát (pl. NullPointerException)
            println("Hiba a kép lekérésekor: ${e.message}")
            ResponseEntity.status(500).build()
        }
    }



    @PostMapping("/contact/{id}")
    fun uploadContactImage(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile
    ): String {
        val user = userService.currentUser()

        val contact = contactRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Contact not found") }

        if (contact.user.id != user.id) {
            throw IllegalStateException("You do not own this contact")
        }

        val path = imageService.saveContactImage(contact.id!!, file)

        val updated = contact.copy(imagePath = path)
        contactRepository.save(updated)

        return path
    }
}

