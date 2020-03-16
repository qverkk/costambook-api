package com.qverkk.costambookapi.repository

import com.qverkk.costambookapi.model.Authorities
import com.qverkk.costambookapi.model.AuthoritiesDTO
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authorities, String> {
    fun findByUsername(username: String): AuthoritiesDTO?
}