package com.qverkk.costambookapi.users

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotNull
        @Column(name = "id")
        val id: Long,
        @NotNull
        @Column(name = "username")
        val username: String,
        @NotNull
        @Column(name = "password")
        @Size(min = 8, max = 50)
        val password: String,
        @NotNull
        @Column(name = "firstName")
        @Size(min = 1, max = 30)
        val firstName: String,
        @NotNull
        @Column(name = "lastName")
        @Size(min = 1, max = 30)
        val lastName: String
)

data class UserDTO(
        val id: Long,
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String
)
