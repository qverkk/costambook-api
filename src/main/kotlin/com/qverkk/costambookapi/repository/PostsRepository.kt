package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.Post
import com.qverkk.costambookapi.model.PostDTO
import com.qverkk.costambookapi.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface PostsRepository : JpaRepository<Post, Long> {
    fun findByPostId(postId: Long): PostDTO?
    fun findAllByUser(user: User): List<PostDTO>
    fun findAllByPostIdAfter(id: Long): List<PostDTO>
}