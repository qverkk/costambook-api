package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findUserByUserId(userId: Long): User?
    fun findUserByUsername(username: String): User?
}
