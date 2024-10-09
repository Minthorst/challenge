package jan.challenge.config

import jan.challenge.model.Role
import jan.challenge.model.User
import jan.challenge.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class AdminUserInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AdminUserInitializer::class.java)
    }

    @Bean
    fun initializeAdminUser(): CommandLineRunner {
        return CommandLineRunner {
            if (userRepository.findUserByUsername("admin") == null) {
                val adminUser = User(
                    id = null,
                    username = "admin",
                    password = passwordEncoder.encode("admin"),
                    role = Role.ADMIN
                )
                userRepository.save(adminUser)
                logger.info("Default admin user created with username 'admin' and role 'ADMIN'.")
            }
        }
    }
}