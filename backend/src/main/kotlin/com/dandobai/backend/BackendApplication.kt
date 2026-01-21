package com.dandobai.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.github.cdimascio.dotenv.dotenv

@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
	val dotenv = dotenv {
		ignoreIfMalformed = true
		ignoreIfMissing = true // Fontos: CI/CD környezetben (pl. Docker) nem lesz .env fájl
	}

	// Ez betölti a .env tartalmát a rendszer környezeti változói közé
	dotenv.entries().forEach { System.setProperty(it.key, it.value) }

	dotenv.entries().forEach { entry ->
		System.setProperty(entry.key, entry.value)
	}

	runApplication<BackendApplication>(*args)
}
