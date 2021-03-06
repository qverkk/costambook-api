package com.qverkk.costambookapi.controller

import com.qverkk.costambookapi.model.User
import com.qverkk.costambookapi.model.UserDTO
import com.qverkk.costambookapi.model.UserLogin
import com.qverkk.costambookapi.service.JpaUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var service: JpaUserService

    @GetMapping(
            params = ["id"]
    )
    fun getUserInformationForId(@RequestParam id: Long): User? {
        return service.findUserById(id)
    }

    @PostMapping(
            value = ["/register"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun registerNewUser(@RequestBody userDTO: UserDTO): ResponseEntity<Any> {
        return service.registerNewUserAccount(userDTO)
    }

    @PostMapping(
            value = ["/login"],
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(@RequestBody userLogin: UserLogin): ResponseEntity<Any> {
        return service.login(userLogin.username, userLogin.password)
    }

    @GetMapping(
            value = ["/test"]
    )
    fun test(): String {
        return "Hello world"
    }

    @GetMapping(
            value = ["/current"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun getCurrentUser(request: HttpServletRequest): User? {
        val principal = request.userPrincipal
        return service.findUserByUsername(principal.name)
    }

    @GetMapping(
            value = ["/validate"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            params = ["token"]
    )
    fun validateToken(@RequestParam token: String): Boolean {
        return try {
            service.isTokenValid(token)
        } catch (e: Exception) {
            false
        }
    }
}
