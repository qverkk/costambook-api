package com.qverkk.costambookapi.controller

import com.qverkk.costambookapi.model.Post
import com.qverkk.costambookapi.model.PostDTO
import com.qverkk.costambookapi.service.JpaPostsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/posts")
class PostsController {

    @Autowired
    private lateinit var service: JpaPostsService

    @GetMapping()
    fun getAllPosts(): List<PostDTO> {
        return service.findAll()
    }

    @GetMapping(
            params = ["userId"]
    )
    fun getPostsByUser(@RequestParam userId: Long): List<PostDTO> {
        return service.findPostsByUserId(userId)
    }

    @PostMapping()
    fun createPost(@RequestBody wrapper: FormWrapper, request: HttpServletRequest): Boolean {
        val principal = request.userPrincipal
        return service.createPost(Post(user = null, postId = -1, description = wrapper.description, image = wrapper.file?.toByteArray()), principal.name)
    }

    data class FormWrapper(
            val description: String,
            val file: String?
    )
}