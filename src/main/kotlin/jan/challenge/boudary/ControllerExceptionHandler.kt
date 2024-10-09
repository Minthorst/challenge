package jan.challenge.boudary

import jan.challenge.exceptions.ProductNotFoundException
import jan.challenge.exceptions.ProductNotValidException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler
    fun handleProductNotValidException(ex: ProductNotValidException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<String> {
        return ResponseEntity("product with id ${ex.productId} was not found", HttpStatus.BAD_REQUEST)
    }

}