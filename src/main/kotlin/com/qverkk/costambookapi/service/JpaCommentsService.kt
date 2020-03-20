package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.controller.CommentsWrapper
import com.qverkk.costambookapi.model.CommentsDTO
import com.qverkk.costambookapi.repository.CommentsRepository
import org.springframework.stereotype.Service

@Service("Comments service")
class JpaCommentsService(
        val commentsRepository: CommentsRepository
): CommentsService {
    override fun findCommentsByPostId(postId: Long): List<CommentsDTO> {
        return commentsRepository.findAllByPostId(postId)
    }

    override fun addNewComment(wrapper: CommentsWrapper) {
        return commentsRepository.addNewComment(wrapper.userId, wrapper.postId, wrapper.text)
    }

}