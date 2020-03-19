package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.constants.JwtUtils
import com.qverkk.costambookapi.model.Post
import com.qverkk.costambookapi.model.PostDTO
import com.qverkk.costambookapi.model.User
import com.qverkk.costambookapi.repository.PostsRepository
import com.qverkk.costambookapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("Posts service")
class JpaPostsService(val postsRepository: PostsRepository, val userRepository: UserRepository) : PostsService {

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    override fun findAll(): List<PostDTO> {
        return postsRepository.findAllByPostIdAfter(0)
    }

    override fun findPostsByUserId(userId: Long): List<PostDTO> {
        val userDto = userRepository.findUserByUserId(userId) ?: return emptyList()
        val user = User(
                userDto.userId,
                userDto.username,
                userDto.password,
                userDto.enabled,
                userDto.firstName,
                userDto.lastName
        )
        return postsRepository.findAllByUser(user)
    }

    override fun findPostsByUsername(username: String): List<PostDTO> {
        val user = getUserByUsername(username) ?: return emptyList()
        return postsRepository.findAllByUser(user)
    }

    override fun createPost(post: Post, username: String): Boolean {
        val user = getUserByUsername(username) ?: return false
        post.user = user
        postsRepository.save(post)
        return true
    }

    override fun findByPostId(postId: Long): Post? {
        return postsRepository.findByPostId(postId)
    }

    private fun getUserByUsername(username: String): User? {
        val userDto = userRepository.findUserByUsername(username) ?: return null
        return User(
                userDto.userId,
                userDto.username,
                userDto.password,
                userDto.enabled,
                userDto.firstName,
                userDto.lastName
        )
    }
}