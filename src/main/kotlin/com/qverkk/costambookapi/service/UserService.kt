package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.model.UserDTO
import org.springframework.http.ResponseEntity

interface UserService {
    fun login(username: String, password: String): ResponseEntity<Any>
    fun registerNewUserAccount(user: UserDTO): ResponseEntity<Any>
    fun findUserByUsername(name: String): UserDTO?
    fun findUserById(id: Long): UserDTO?
    fun isTokenValid(token: String): Boolean
}
