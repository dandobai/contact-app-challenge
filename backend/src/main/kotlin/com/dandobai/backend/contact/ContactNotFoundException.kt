package com.dandobai.backend.contact

class ContactNotFoundException(id: Long) :
        RuntimeException("Contact with id=$id not found")