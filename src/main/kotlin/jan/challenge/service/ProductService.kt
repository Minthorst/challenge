package jan.challenge.service

import jan.challenge.boudary.dtos.GetProductResponse
import jan.challenge.boudary.dtos.ProductRequest
import jan.challenge.boudary.dtos.UpdateProductRequest
import jan.challenge.boudary.dtos.toProduct
import jan.challenge.exceptions.ProductNotFoundException
import jan.challenge.exceptions.ProductNotValidException
import jan.challenge.model.toGetProductResponse
import jan.challenge.repository.ProductRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun saveProduct(productRequest: ProductRequest): GetProductResponse {
        validateProductRequest(productRequest)
        val savedProduct = productRepository.save(productRequest.toProduct())
        return savedProduct.toGetProductResponse()
    }

    private fun validateProductRequest(request: ProductRequest) {
        if (request.priceInEur < BigDecimal.ZERO) throw ProductNotValidException("product price must not be negative")
        if (request.stock < 0) throw ProductNotValidException("product stock must be positive")
    }

    fun findAllProducts(): List<GetProductResponse> {
        val foundProducts = productRepository.findAll()
        return foundProducts.map { it.toGetProductResponse() }
    }

    fun findProductById(id: Long): GetProductResponse {
        val foundProduct = productRepository.findProductById(id) ?: throw ProductNotFoundException(id)
        return foundProduct.toGetProductResponse()
    }

    fun updateProduct(id: Long, updateProductRequest: UpdateProductRequest): GetProductResponse {
        validateUpdateProductRequest(updateProductRequest)
        val foundProduct = productRepository.findProductById(id) ?: throw ProductNotFoundException(id)

        val updatedProduct = foundProduct.copy(
            name = updateProductRequest.name ?: foundProduct.name,
            description = updateProductRequest.description ?: foundProduct.description,
            priceInEur = updateProductRequest.priceInEur ?: foundProduct.priceInEur,
            stock = updateProductRequest.stock ?: foundProduct.stock
        )
        return productRepository.save(updatedProduct).toGetProductResponse()
    }

    private fun validateUpdateProductRequest(request: UpdateProductRequest) {
        request.priceInEur?.let { if (it < BigDecimal.ZERO) throw ProductNotValidException("product price must not be negative") }
        request.stock?.let { if (it < 0) throw ProductNotValidException("product stock must be positive") }
    }

    fun deleteProduct(id: Long) {
        productRepository.findProductById(id) ?: throw ProductNotFoundException(id)
        productRepository.deleteById(id)
    }
}