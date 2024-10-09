package jan.challenge.boudary

import jan.challenge.boudary.dtos.UserRequest
import jan.challenge.boudary.dtos.UserResponse
import jan.challenge.boudary.dtos.toUser
import jan.challenge.model.toUserResponse
import jan.challenge.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): UserResponse =
        userService.createUser(userRequest.toUser())
            ?.toUserResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")

    @GetMapping
    fun listAll(): List<UserResponse> =
        userService.findAll()
            .map { it.toUserResponse() }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserResponse =
        userService.findById(id)
            ?.toUserResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) {
        userService.deleteById(id)
    }
}