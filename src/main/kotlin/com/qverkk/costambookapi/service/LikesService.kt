package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.controller.LikeWrapper
import com.qverkk.costambookapi.model.LikesDTO
import com.qverkk.costambookapi.model.Post

interface LikesService {
    fun getLikesForPost(post: Post): List<LikesDTO>
    fun performLike(wrapper: LikeWrapper): Boolean
}