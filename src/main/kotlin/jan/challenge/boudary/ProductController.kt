package jan.challenge.boudary

import jan.challenge.boudary.dtos.GetProductResponse
import jan.challenge.boudary.dtos.ProductRequest
import jan.challenge.boudary.dtos.UpdateProductRequest
import jan.challenge.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    companion object {
        private val logger = LoggerFactory.getLogger(ProductController::class.java)
    }

    @GetMapping("")
    fun getAllProducts(): ResponseEntity<List<GetProductResponse>> {
        logger.info("getAllProducts")
        return ResponseEntity.ok(productService.findAllProducts())
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<GetProductResponse> {
        logger.info("getProductById $id")
        return ResponseEntity.ok(productService.findProductById(id))
    }

    @PostMapping("")
    fun saveProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<GetProductResponse> {
        logger.info("saveProduct $productRequest")
        return ResponseEntity.ok(
            productService.saveProduct(productRequest)
        )
    }

    @PutMapping("/{id}")
    fun updateProductById(
        @PathVariable id: Long,
        @RequestBody updateProductRequest: UpdateProductRequest
    ): ResponseEntity<GetProductResponse> {
        logger.info("updateProductById $id, $updateProductRequest")
        return ResponseEntity.ok(productService.updateProduct(id, updateProductRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteProductById(@PathVariable id: Long) {
        logger.info("deleteProductById $id")
        productService.deleteProduct(id)
    }
}