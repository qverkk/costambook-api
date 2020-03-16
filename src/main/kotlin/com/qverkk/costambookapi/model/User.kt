package com.qverkk.costambookapi.model

import com.qverkk.costambookapi.constants.Authorities
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id", unique = true, nullable = false)
        val userId: Long?,
        @Column(name = "username", nullable = false)
        val username: String,
        @Column(name = "password", nullable = false)
        @Size(min = 8, max = 50)
        val password: String,
        @Column(name = "enabled")
        val enabled: Boolean,
        @Column(name = "first_name", nullable = false)
        @Size(min = 1, max = 30)
        val firstName: String,
        @Column(name = "last_name", nullable = false)
        @Size(min = 1, max = 30)
        val lastName: String
) {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user", fetch = FetchType.EAGER)
    val posts = mutableListOf<Post>()
}

data class UserDTO(
        val userId: Long,
        val username: String,
        val password: String,
        val enabled: Boolean,
        val firstName: String,
        val lastName: String
) {
        val posts: MutableList<Post>? = mutableListOf()
}

data class UserLogin(
        val username: String,
        val password: String
)

@Entity
@Table(name = "authorities")
data class Authorities(
        @Id
        val username: String,
        val authority: Authorities
) {
    constructor() : this("admin", Authorities.ADMIN)
}

data class AuthoritiesDTO(
        val username: String,
        val authority: Authorities
)
