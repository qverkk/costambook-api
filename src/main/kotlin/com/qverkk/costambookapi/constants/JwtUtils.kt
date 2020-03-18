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
            val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""))
            return true
        } catch (exception: ExpiredJwtException) {
            println("Request to parse expired JWT : $token failed : ${exception.message}")
        } catch (exception: UnsupportedJwtException) {
            println("Request to parse unsupported JWT : $token failed : ${exception.message}")
        } catch (exception: MalformedJwtException) {
            println("Request to parse invalid JWT : $token failed : ${exception.message}")
        } catch (exception: SignatureException) {
            println("Request to parse JWT with invalid signature : $token failed : ${exception.message}")
        } catch (exception: IllegalArgumentException) {
            println("Request to parse empty or null JWT : $token failed : ${exception.message}")
        }
        return false
    }
}