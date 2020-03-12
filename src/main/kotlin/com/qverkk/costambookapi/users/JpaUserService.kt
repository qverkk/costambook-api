package com.qverkk.costambookapi.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service("User service")
class JpaUserService(val repository: UserRepository): UserService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun login(username: String, password: String): ResponseEntity<Any> {
        val user = repository.findUserByUsername(username)
                ?: return ResponseEntity("User doesn't exist", HttpStatus.NOT_FOUND)
        if (passwordEncoder.matches(password, user.password).not()) {
            return ResponseEntity("Password doesn't match", HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity(user, HttpStatus.OK)
    }

    override fun registerNewUserAccount(user: UserDTO): ResponseEntity<Any> {
        if (userExists(user.username)) {
            return ResponseEntity("User already exists", HttpStatus.CONFLICT)
        }
        val newUser = User(
            null,
                user.username,
                passwordEncoder.encode(user.password),
                user.firstName,
                user.lastName
        );
        val createdUser = repository.save(newUser)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    fun userExists(username: String): Boolean {
        return repository.findUserByUsername(username) != null
    }
}
