package com.qverkk.costambookapi.users

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findUserByUserId(userId: Long): UserDTO?
    fun findUserByUsername(username: String): UserDTO?
}
