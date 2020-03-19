package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.controller.LikeWrapper
import com.qverkk.costambookapi.model.Likes
import com.qverkk.costambookapi.model.LikesDTO
import com.qverkk.costambookapi.model.Post
import com.qverkk.costambookapi.repository.LikesRepository
import com.qverkk.costambookapi.repository.PostsRepository
import com.qverkk.costambookapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service("Likes service")
class JpaLikesService(
        private val likeRepository: LikesRepository,
        private val userRepository: UserRepository,
        private val postsRepository: PostsRepository
) : LikesService {

    override fun getLikesForPost(post: Post): List<LikesDTO> {
        return likeRepository.findAllByPost(post)
    }

    override fun performLike(wrapper: LikeWrapper): Boolean {
        val orElse = likeRepository.getLikeByUserIdAndPostId(wrapper.userId, wrapper.postId)
        if (orElse != null) {
            likeRepository.setLikeByUserAndPost(wrapper.userId, wrapper.postId, wrapper.likeType)
            return true
        }
        likeRepository.saveNewLike(wrapper.userId, wrapper.postId, wrapper.likeType.ordinal)
        return true
    }
}