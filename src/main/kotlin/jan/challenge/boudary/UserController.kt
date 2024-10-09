package jan.challenge.boudary

import jan.challenge.boudary.dtos.UserRequest
import jan.challenge.boudary.dtos.UserResponse
import jan.challenge.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    companion object {
        private val logger = LoggerFactory.getLogger(ProductController::class.java)
    }

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        logger.info("createUser ${userRequest.username}")
        return ResponseEntity.ok(userService.createUser(userRequest))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        logger.info("getAllUsers")
        return ResponseEntity.ok(userService.findAll())
    }

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        logger.info("findUserById $id")
        return ResponseEntity.ok(userService.findById(id))
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long) {
        logger.info("deleteUserById $id")
        userService.deleteById(id)
    }
}