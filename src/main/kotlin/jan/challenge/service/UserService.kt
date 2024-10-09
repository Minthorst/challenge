package jan.challenge.service

import jan.challenge.boudary.dtos.UserRequest
import jan.challenge.boudary.dtos.UserResponse
import jan.challenge.boudary.dtos.toUser
import jan.challenge.exceptions.UserNotFoundException
import jan.challenge.exceptions.UserNotValidException
import jan.challenge.model.toUserResponse
import jan.challenge.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(userRequest: UserRequest): UserResponse {
        if (repository.findUserByUsername(userRequest.username) != null) {
            throw UserNotValidException("User with this name already exists")
        }
        val encodedUser = userRequest.copy(password = passwordEncoder.encode(userRequest.password))
        return repository.save(encodedUser.toUser()).toUserResponse()

    }

    fun findById(id: Long): UserResponse =
        repository.findUserById(id)?.toUserResponse()
            ?: throw UserNotFoundException(id)

    fun findAll(): List<UserResponse> =
        repository.findAll().map { it.toUserResponse() }

    fun deleteById(id: Long) {
        repository.findUserById(id) ?: throw UserNotFoundException(id)
        repository.deleteUserById(id)
    }
}