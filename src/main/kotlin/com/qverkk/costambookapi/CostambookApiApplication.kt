package com.qverkk.costambookapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
@EnableWebMvc
class CostambookApiApplication : WebSecurityConfigurerAdapter(), WebMvcConfigurer {
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(11)
    }

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui").permitAll()
                .antMatchers("/user/login*", "/user/register*").permitAll()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    override fun userDetailsService(): UserDetailsService? {
        return super.userDetailsService()
    }
}

fun main(args: Array<String>) {
    runApplication<CostambookApiApplication>(*args)
}
