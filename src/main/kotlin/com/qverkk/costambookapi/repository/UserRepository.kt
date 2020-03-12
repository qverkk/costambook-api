package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.User
import com.qverkk.costambookapi.model.UserDTO
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findUserByUserId(userId: Long): UserDTO?
    fun findUserByUsername(username: String): UserDTO?
}
