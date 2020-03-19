package com.qverkk.costambookapi.controller

import com.qverkk.costambookapi.model.LikeType
import com.qverkk.costambookapi.model.LikesDTO
import com.qverkk.costambookapi.service.JpaLikesService
import com.qverkk.costambookapi.service.JpaPostsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/likes")
class LikesController {

    @Autowired
    private lateinit var service: JpaLikesService
    @Autowired
    private lateinit var postService: JpaPostsService

    @GetMapping()
    fun getLikesForPost(@RequestParam postId: Long): List<LikesDTO> {
        val post = postService.findByPostId(postId) ?: return emptyList()
        return service.getLikesForPost(post)
    }

    @PostMapping()
    fun likeOrDislikePost(@RequestBody wrapper: LikeWrapper): Boolean {
        return service.performLike(wrapper)
    }
}

data class LikeWrapper(
        val userId: Long,
        val postId: Long,
        val likeType: LikeType
)
