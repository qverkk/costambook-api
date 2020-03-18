package com.qverkk.costambookapi.service

import com.qverkk.costambookapi.constants.JwtUtils
import com.qverkk.costambookapi.constants.SecurityConstants
import com.qverkk.costambookapi.model.Authorities
import com.qverkk.costambookapi.model.JwtResponse
import com.qverkk.costambookapi.model.User
import com.qverkk.costambookapi.model.UserDTO
import com.qverkk.costambookapi.repository.AuthorityRepository
import com.qverkk.costambookapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service("User service")
class JpaUserService(val repository: UserRepository, val authorityRepository: AuthorityRepository) : UserService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtUtils: JwtUtils

    override fun login(username: String, password: String): ResponseEntity<Any> {
        val user = repository.findUserByUsername(username)
                ?: return ResponseEntity("User doesn't exist", HttpStatus.OK)
        if (passwordEncoder.matches(password, user.password).not()) {
            return ResponseEntity("Password doesn't match", HttpStatus.OK)
        }
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtUtils.generateToken(authentication)
        return ResponseEntity(
                JwtResponse(
                        SecurityConstants.TOKEN_PREFIX + token,
                        user.userId,
                        user.username,
                        user.firstName,
                        user.lastName
                )
                , HttpStatus.OK)
    }

    override fun registerNewUserAccount(user: UserDTO): ResponseEntity<Any> {
        if (userExists(user.username)) {
            return ResponseEntity("User already exists", HttpStatus.OK)
        }
        val newUser = User(
                null,
                user.username,
                passwordEncoder.encode(user.password),
                true,
                user.firstName,
                user.lastName
        )
        val createdUser = repository.save(newUser)
        authorityRepository.save(Authorities(createdUser.username, com.qverkk.costambookapi.constants.Authorities.USER))
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    fun userExists(username: String): Boolean {
        return repository.findUserByUsername(username) != null
    }

    override fun findUserByUsername(name: String): UserDTO? {
        return repository.findUserByUsername(name)
    }

    override fun findUserById(id: Long): UserDTO? {
        return repository.findUserByUserId(id)
    }

    override fun isTokenValid(token: String): Boolean {
        return jwtUtils.validateToken(token)
    }
}
