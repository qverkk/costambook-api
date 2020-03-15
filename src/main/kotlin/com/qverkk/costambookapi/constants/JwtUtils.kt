package com.qverkk.costambookapi.constants

import com.qverkk.costambookapi.filter.JwtAuthorizationFilter
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*
import java.util.stream.Collectors

class JwtUtils {
    fun generateToken(authentication: Authentication): String {
        val user = authentication.principal as User
        val roles = user.authorities
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.toList())
        val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
        val token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.username)
                .setExpiration(Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact()
        return token
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts
                .parserBuilder()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .build()
                    .parseClaimsJws(token)
            return true
        } catch (exception: ExpiredJwtException) {
            log.warn("Request to parse expired JWT : {} failed : {}", token, exception.message)
        } catch (exception: UnsupportedJwtException) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.message)
        } catch (exception: MalformedJwtException) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.message)
        } catch (exception: SignatureException) {
            log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.message)
        } catch (exception: IllegalArgumentException) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.message)
        }

        return false
    }


    companion object {
        private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)
    }
}