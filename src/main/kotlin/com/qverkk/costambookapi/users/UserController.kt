package com.qverkk.costambookapi.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var service: JpaUserService

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
            consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(@RequestBody userLogin: UserLogin): ResponseEntity<Any> {
        return service.login(userLogin.username, userLogin.password)
    }
}
