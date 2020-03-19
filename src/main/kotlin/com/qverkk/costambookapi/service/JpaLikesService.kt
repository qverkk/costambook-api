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
        val user = userRepository.findUserByUserId(wrapper.userId) ?: return false
        val post = postsRepository.findByPostId(wrapper.postId) ?: return false
        val like = Likes(
                null,
                post,
                user,
                wrapper.likeType
        )
        likeRepository.save(like)
        return true
    }
}