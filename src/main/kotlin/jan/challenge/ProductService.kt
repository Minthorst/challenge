package jan.challenge

import jan.challenge.boudary.CreateProductRequest
import jan.challenge.boudary.GetProductResponse
import jan.challenge.boudary.UpdateProductRequest
import jan.challenge.boudary.toProduct
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun saveProduct(productRequest: CreateProductRequest): GetProductResponse {
        val savedProduct = productRepository.save(productRequest.toProduct())
        return savedProduct.toGetProductResponse()
    }

    fun findAllProducts(): List<GetProductResponse> {
        val foundProducts = productRepository.findAll().toList()
        return foundProducts.map { it.toGetProductResponse() }
    }

    fun findProductById(id: Long): GetProductResponse {
        val foundProduct = productRepository.findProductById(id)?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return foundProduct.toGetProductResponse()
    }

    fun updateProduct(id: Long, updateProductRequest: UpdateProductRequest): GetProductResponse {
        val foundProduct = productRepository.findProductById(id)?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val updatedProduct = foundProduct.copy(
            name = updateProductRequest.name ?: foundProduct.name,
            description = updateProductRequest.description ?: foundProduct.description,
            priceInEur = updateProductRequest.priceInEur ?: foundProduct.priceInEur,
            stock = updateProductRequest.stock ?: foundProduct.stock
        )
        return productRepository.save(updatedProduct).toGetProductResponse()
    }

    fun deleteProduct(id: Long) {
        productRepository.deleteById(id)
    }
}