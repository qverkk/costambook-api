package com.qverkk.costambookapi.controller

import com.qverkk.costambookapi.model.Post
import com.qverkk.costambookapi.model.PostDTO
import com.qverkk.costambookapi.service.JpaPostsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/posts")
class PostsController {

    @Autowired
    private lateinit var service: JpaPostsService

    @GetMapping()
    fun getAllPosts(): List<PostDTO> {
        val posts = service.findAll()
        val result = mutableListOf<PostDTO>()
        posts.forEach {
            result.add(
                    PostDTO(
                            it.postId,
                            it.user,
                            it.description,
                            it.image?.let { it1 -> decompressImage(it1) }
                    )
            )
        }
        return result
    }

    @GetMapping(
            params = ["userId"]
    )
    fun getPostsByUser(@RequestParam userId: Long): List<PostDTO> {
        val posts = service.findPostsByUserId(userId)
        val result = mutableListOf<PostDTO>()
        posts.forEach {
            result.add(
                    PostDTO(
                            it.postId,
                            it.user,
                            it.description,
                            it.image?.let { it1 -> decompressImage(it1) }
                    )
            )
        }
        return result
    }

    @PostMapping()
    fun createPost(@RequestParam("description") description: String, @RequestParam("imageFile") file: MultipartFile?, request: HttpServletRequest): Boolean {
        val principal = request.userPrincipal
        val post = Post(
                postId = -1,
                user = null,
                description = description,
                image = file?.bytes?.let { compressImage(it) }
        )
        return service.createPost(post, principal.name)
    }

    private fun compressImage(bytes: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setInput(bytes)
        deflater.finish()

        val outputStream = ByteArrayOutputStream(bytes.size)
        val buffer = ByteArray(1024)

        while (!deflater.finished()) {
            val count = deflater.deflate(buffer)
            outputStream.write(buffer, 0, count)
        }

        outputStream.close()

        println("Finished compressing image")
        return outputStream.toByteArray()
    }

    private fun decompressImage(bytes: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(bytes)

        val outputStream = ByteArrayOutputStream(bytes.size)
        val buffer = ByteArray(1024)

        try {
            while (!inflater.finished()) {
                val count = inflater.inflate(buffer)
                outputStream.write(buffer, 0, count)
            }
            outputStream.close()
        } catch (e: Exception) {

        }
        println("Finished decompressing image")
        return outputStream.toByteArray()
    }

    data class FormWrapper(
            val description: String,
            val file: MultipartFile?
    )
}