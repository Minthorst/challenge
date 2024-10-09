package jan.challenge.boudary.dtos

import jan.challenge.model.Role
import jan.challenge.model.User

data class UserRequest(
    val username: String,
    val password: String,
)

fun UserRequest.toUser(): User = User(
    null, username, password, Role.USER
)