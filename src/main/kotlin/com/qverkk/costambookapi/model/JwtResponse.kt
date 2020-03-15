package com.qverkk.costambookapi.model

data class JwtResponse(
        val token: String,
        val userId: Long,
        val username: String,
        val firstName: String,
        val lastName: String
)
