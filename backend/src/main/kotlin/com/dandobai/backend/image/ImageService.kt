package com.dandobai.backend.image

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception

@Service
class ImageService(
    @Value("\${aws.s3.bucket}") private val bucketName: String,
    @Value("\${aws.region}") private val region: String,
    @Value("\${aws.access-Key}") private val accessKey: String,
    @Value("\${aws.secret-Key}") private val secretKey: String
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val s3Client: S3Client by lazy {
        log.info("S3Client inicializálása a következő régióban: $region")
        S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
                )
            )
            .build()
    }

    fun saveUserImage(userId: Long, file: MultipartFile): String {
        val filename = "user_${userId}_${System.currentTimeMillis()}.png"
        val key = "users/$filename"
        uploadToS3(key, file)
        return key
    }

    fun saveContactImage(contactId: Long, file: MultipartFile): String {
        val filename = "contact_${contactId}_${System.currentTimeMillis()}.png"
        val key = "contacts/$filename"
        uploadToS3(key, file)
        return key
    }

    private fun uploadToS3(key: String, file: MultipartFile) {
        try {
            log.info("Feltöltés indítása: $key a $bucketName bucketbe...")

            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.contentType ?: "image/png")
                // .acl("public-read") // KOMMENTELD KI ezt a sort, ha a bucket tiltja az ACL-eket!
                .build()

            s3Client.putObject(
                putObjectRequest,
                RequestBody.fromBytes(file.bytes)
            )

            log.info("Sikeres feltöltés: $key")

        } catch (e: S3Exception) {
            log.error("AWS S3 hiba történt!")
            log.error("Hibaüzenet: ${e.awsErrorDetails().errorMessage()}")
            log.error("AWS Hibakód: ${e.awsErrorDetails().errorCode()}")
            log.error("HTTP státuszkód: ${e.statusCode()}")
            throw RuntimeException("S3 feltöltési hiba: ${e.awsErrorDetails().errorMessage()}")
        } catch (e: Exception) {
            log.error("Váratlan hiba a fájl feltöltése közben: ${e.message}", e)
            throw e
        }
    }
}
