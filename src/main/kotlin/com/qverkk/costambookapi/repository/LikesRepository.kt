package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.Likes
import com.qverkk.costambookapi.model.LikesDTO
import com.qverkk.costambookapi.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface LikesRepository : JpaRepository<Likes, Long> {
    fun findAllByPost(post: Post): List<LikesDTO>
    fun findAllByPostEquals(post: Post): List<Likes>
}