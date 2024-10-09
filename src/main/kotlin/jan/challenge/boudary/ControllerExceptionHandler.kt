package jan.challenge.boudary

import jan.challenge.exceptions.ProductNotFoundException
import jan.challenge.exceptions.ProductNotValidException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(ProductController::class.java)
    }

    @ExceptionHandler
    fun handleProductNotValidException(ex: ProductNotValidException): ResponseEntity<String> {
        logError(ex.message)
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<String> {
        logError(ex.message)
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<String> {
        logger.error("Exception occurred: ${ex.message}", ex)
        return ResponseEntity("An error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun logError(exceptionMessage: String?) {
        logger.error("Exception occurred: $exceptionMessage")
    }

}