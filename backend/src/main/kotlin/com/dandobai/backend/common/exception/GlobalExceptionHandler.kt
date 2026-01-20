package com.dandobai.backend.common.exceptions

import com.dandobai.backend.contact.ContactNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    data class ErrorResponse(
        val error: String,
        val status: Int,
        val timestamp: LocalDateTime = LocalDateTime.now()
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val message = ex.bindingResult.fieldErrors.joinToString(", ") {
            "${it.field}: ${it.defaultMessage}"
        }
        return ResponseEntity(
            ErrorResponse(message, HttpStatus.BAD_REQUEST.value()),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ContactNotFoundException::class)
    fun handleContactNotFound(ex: ContactNotFoundException) =
        ResponseEntity(
            ErrorResponse(ex.message ?: "Contact not found", 404),
            HttpStatus.NOT_FOUND
        )

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException) =
        ResponseEntity(
            ErrorResponse(ex.message ?: "Invalid request", 400),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(ex: IllegalStateException) =
        ResponseEntity(
            ErrorResponse(ex.message ?: "Invalid state", 400),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception) =
        ResponseEntity(
            ErrorResponse("Unexpected error occurred", 500),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
}
