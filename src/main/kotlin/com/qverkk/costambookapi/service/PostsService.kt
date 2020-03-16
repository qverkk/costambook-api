package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.model.Post
import com.qverkk.costambookapi.model.PostDTO

interface PostsService {
    fun findAll(): List<PostDTO>
    fun findPostsByUserId(userId: Long): List<PostDTO>
    fun findPostsByUsername(username: String): List<PostDTO>
    fun createPost(post: Post, username: String): Boolean
}