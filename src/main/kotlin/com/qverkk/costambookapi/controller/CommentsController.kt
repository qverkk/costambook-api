package com.qverkk.costambookapi.controller

import com.qverkk.costambookapi.model.CommentsDTO
import com.qverkk.costambookapi.service.JpaCommentsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentsController {

    @Autowired
    private lateinit var service: JpaCommentsService

    @PostMapping
    fun addNewComment(@RequestBody wrapper: CommentsWrapper) {
        service.addNewComment(wrapper)
    }

    @GetMapping
    fun getCommentsForPostId(@RequestParam postId: Long): List<CommentsDTO> {
        return service.findCommentsByPostId(postId)
    }
}

data class CommentsWrapper(
        val postId: Long,
        val userId: Long,
        val text: String
)