package com.qverkk.costambookapi.users

import org.springframework.http.ResponseEntity

interface UserService {
    fun login(username: String, password: String): ResponseEntity<Any>
    fun registerNewUserAccount(user: UserDTO): ResponseEntity<Any>
}
