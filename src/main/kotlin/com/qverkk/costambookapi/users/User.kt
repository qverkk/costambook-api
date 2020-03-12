package com.qverkk.costambookapi.users

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        val userId: Long?,
        @NotNull
        @Column(name = "username")
        val username: String,
        @NotNull
        @Column(name = "password")
        @Size(min = 8, max = 50)
        val password: String,
        @NotNull
        @Column(name = "first_name")
        @Size(min = 1, max = 30)
        val firstName: String,
        @NotNull
        @Column(name = "last_name")
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
