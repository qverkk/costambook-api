package com.qverkk.costambookapi.users

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "user_id", unique = true, nullable = false)
        val userId: Long?,
        @Column(name = "username", nullable = false)
        val username: String,
        @Column(name = "password", nullable = false)
        @Size(min = 8, max = 50)
        val password: String,
        @Column(name = "first_name", nullable = false)
        @Size(min = 1, max = 30)
        val firstName: String,
        @Column(name = "last_name", nullable = false)
        @Size(min = 1, max = 30)
        val lastName: String
)

data class UserDTO(
        val userId: Long,
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String
)

data class UserLogin(
        val username: String,
        val password: String
)
