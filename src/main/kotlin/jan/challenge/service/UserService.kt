package jan.challenge.service

import jan.challenge.model.User
import jan.challenge.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(user: User): User? {
        val found = repository.findUserByUsername(user.username)
        return if (found == null) {
            val encodedUser = user.copy(password = passwordEncoder.encode(user.password))
            repository.save(encodedUser)
        } else null
    }

    fun findById(id: Long): User? =
        repository.findUserById(id)

    fun findAll(): List<User> =
        repository.findAll()
            .toList()

    fun deleteById(id: Long) =
        repository.deleteUserById(id)
}