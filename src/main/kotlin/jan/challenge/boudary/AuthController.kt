package jan.challenge.boudary

import jan.challenge.boudary.dtos.AuthenticationRequest
import jan.challenge.boudary.dtos.AuthenticationResponse
import jan.challenge.service.AuthenticationService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AuthController::class.java)
    }

    @PostMapping
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> {
        logger.info("authenticate ${authRequest.username}")
        return ResponseEntity.ok(authenticationService.authentication(authRequest))
    }
}