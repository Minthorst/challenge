package jan.challenge.exceptions

class UserNotFoundException(val userId: Long) : Exception("user with id $userId was not found")