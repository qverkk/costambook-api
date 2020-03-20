package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.controller.CommentsWrapper
import com.qverkk.costambookapi.model.CommentsDTO

interface CommentsService {
    fun findCommentsByPostId(postId: Long): List<CommentsDTO>
    fun addNewComment(wrapper: CommentsWrapper)
}