package jan.challenge.repository

import jan.challenge.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

    fun findUserByUsername(username: String): User?

    fun findUserById(id: Long): User?

    fun deleteUserById(id: Long)
}