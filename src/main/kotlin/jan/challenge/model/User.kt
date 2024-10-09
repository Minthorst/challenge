package jan.challenge.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jan.challenge.boudary.dtos.UserResponse


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val username: String,
    val password: String,
    val role: Role
)
//TODO add third role for editing products assigned by admin
enum class Role {
    USER, EDITOR, ADMIN
}

fun User.toUserResponse(): UserResponse {
    return UserResponse(id!!, username)
}