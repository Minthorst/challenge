package jan.challenge

import jan.challenge.boudary.dtos.GetProductResponse
import jan.challenge.boudary.dtos.ProductRequest
import jan.challenge.boudary.dtos.UpdateProductRequest
import jan.challenge.exceptions.ProductNotFoundException
import jan.challenge.exceptions.ProductNotValidException
import jan.challenge.model.Product
import jan.challenge.repository.ProductRepository
import jan.challenge.service.ProductService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductServiceTest {
    private lateinit var repository: ProductRepository
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        repository = mock()
        productService = ProductService(repository)
    }

    val productName = "thing"
    val productDescription = "some description"
    val productPrice = BigDecimal.valueOf(100)
    val productStock = 5L

    @Test
    fun `saveProduct should handle valid product request`() {
        val productId = 1L

        val validProductRequest = createProductRequest()
        val product = createProduct()

        whenever(repository.save(product)).thenReturn(createProduct(productId))

        val getProductResponse = productService.saveProduct(validProductRequest)

        assertEquals(createProductResponse(), getProductResponse)
    }

    @Test
    fun `saveProduct should handle invalid product price`() {
        val inValidProductRequest = createProductRequest(price = -BigDecimal.valueOf(100))
        val exception = assertThrows<ProductNotValidException> { productService.saveProduct(inValidProductRequest) }
        assertEquals("product price must not be negative", exception.message)
    }

    @Test
    fun `saveProduct should handle invalid product stock`() {
        val inValidProductRequest = createProductRequest(stock = -5L)
        val exception = assertThrows<ProductNotValidException> { productService.saveProduct(inValidProductRequest) }
        assertEquals("product stock must be positive", exception.message)
    }

    @Test
    fun `findAllProducts should find products`() {
        val foundProducts = listOf(createProduct(1L), createProduct(2L), createProduct(3L))
        whenever(repository.findAll()).thenReturn(foundProducts)
        val allProductResponses = productService.findAllProducts()
        val expectedProductResponse =
            listOf(createProductResponse(1L), createProductResponse(2L), createProductResponse(3L))
        assertEquals(expectedProductResponse, allProductResponses)
    }

    @Test
    fun `findAllProducts should handle no found products`() {
        whenever(repository.findAll()).thenReturn(emptyList())
        val allProductResponses = productService.findAllProducts()
        assertEquals(emptyList(), allProductResponses)
    }

    @Test
    fun `findProductById should return found product`() {
        val productId = 1L
        whenever(repository.findProductById(1L)).thenReturn(createProduct(id = productId))
        val productResponse = productService.findProductById(productId)
        assertEquals(createProductResponse(1L), productResponse)
    }

    @Test
    fun `findProductById should handle product not found`() {
        val productId = 1L
        whenever(repository.findProductById(1L)).thenReturn(null)
        assertThrows<ProductNotFoundException> { productService.findProductById(productId) }
    }

    //this is too much repetition and needs a parameterized test
    @Test
    fun `updateProduct should update product name`() {
        val productId = 1L
        val newName = "new name"
        val updateProductRequest = createUpdateProductRequest(name = newName)
        val originalProduct = createProduct(1L)
        val expectedUpdatedProduct = createProduct(id = 1L, name = newName)
        val getProductResponse = createProductResponse(id = 1L, name = newName)

        whenever(repository.findProductById(productId)).thenReturn(originalProduct)
        whenever(repository.save(expectedUpdatedProduct)).thenReturn(expectedUpdatedProduct)

        val updatedProduct = productService.updateProduct(productId, updateProductRequest)
        assertEquals(getProductResponse, updatedProduct)
        verify(repository).save(expectedUpdatedProduct)
    }

    @Test
    fun `updateProduct should update product description`() {
        val productId = 1L
        val newDescription = "new description"
        val updateProductRequest = createUpdateProductRequest(description = newDescription)
        val originalProduct = createProduct(1L)
        val expectedUpdatedProduct = createProduct(id = 1L, description = newDescription)
        val getProductResponse = createProductResponse(id = 1L, description = newDescription)

        whenever(repository.findProductById(productId)).thenReturn(originalProduct)
        whenever(repository.save(expectedUpdatedProduct)).thenReturn(expectedUpdatedProduct)

        val updatedProduct = productService.updateProduct(productId, updateProductRequest)
        assertEquals(getProductResponse, updatedProduct)
        verify(repository).save(expectedUpdatedProduct)
    }

    @Test
    fun `updateProduct should update product price`() {
        val productId = 1L
        val newPrice = BigDecimal(1000)
        val updateProductRequest = createUpdateProductRequest(price = newPrice)
        val originalProduct = createProduct(1L)
        val expectedUpdatedProduct = createProduct(id = 1L, price = newPrice)
        val getProductResponse = createProductResponse(id = 1L, price = newPrice)

        whenever(repository.findProductById(productId)).thenReturn(originalProduct)
        whenever(repository.save(expectedUpdatedProduct)).thenReturn(expectedUpdatedProduct)

        val updatedProduct = productService.updateProduct(productId, updateProductRequest)
        assertEquals(getProductResponse, updatedProduct)
        verify(repository).save(expectedUpdatedProduct)
    }

    @Test
    fun `updateProduct should update product stock`() {
        val productId = 1L
        val newStock = 500L
        val updateProductRequest = createUpdateProductRequest(stock = newStock)
        val originalProduct = createProduct(1L)
        val expectedUpdatedProduct = createProduct(id = 1L, stock = newStock)
        val getProductResponse = createProductResponse(id = 1L, stock = newStock)

        whenever(repository.findProductById(productId)).thenReturn(originalProduct)
        whenever(repository.save(expectedUpdatedProduct)).thenReturn(expectedUpdatedProduct)

        val updatedProduct = productService.updateProduct(productId, updateProductRequest)
        assertEquals(getProductResponse, updatedProduct)
        verify(repository).save(expectedUpdatedProduct)
    }

    @Test
    fun `updateProduct should handle only null parameter`() {
        val productId = 1L
        val updateProductRequest = createUpdateProductRequest()
        val originalProduct = createProduct(1L)
        val expectedUpdatedProduct = createProduct(id = 1L)
        val getProductResponse = createProductResponse(id = 1L)

        whenever(repository.findProductById(productId)).thenReturn(originalProduct)
        whenever(repository.save(expectedUpdatedProduct)).thenReturn(expectedUpdatedProduct)

        val updatedProduct = productService.updateProduct(productId, updateProductRequest)
        assertEquals(getProductResponse, updatedProduct)
        verify(repository).save(expectedUpdatedProduct)
    }

    @Test
    fun `updateProduct should handle invalid product price`() {
        val invalidUpdateProductRequest = createUpdateProductRequest(price = -BigDecimal.valueOf(100))
        val exception =
            assertThrows<ProductNotValidException> { productService.updateProduct(1L, invalidUpdateProductRequest) }
        assertEquals("product price must not be negative", exception.message)
    }

    @Test
    fun `updateProduct should handle invalid product stock`() {
        val invalidUpdateProductRequest = createUpdateProductRequest(stock = -5L)
        val exception =
            assertThrows<ProductNotValidException> { productService.updateProduct(1L, invalidUpdateProductRequest) }
        assertEquals("product stock must be positive", exception.message)
    }

    @Test
    fun `updateProduct should handle invalid product id`() {
        val validUpdateProductRequest = createUpdateProductRequest()
        assertThrows<ProductNotFoundException> { productService.updateProduct(1L, validUpdateProductRequest) }
    }

    private fun createProductRequest(
        name: String = productName,
        description: String = productDescription,
        price: BigDecimal = productPrice,
        stock: Long = productStock
    ): ProductRequest {
        return ProductRequest(
            name = name,
            description = description,
            priceInEur = price,
            stock = stock
        )
    }

    private fun createProduct(
        id: Long? = null,
        name: String = productName,
        description: String = productDescription,
        price: BigDecimal = productPrice,
        stock: Long = productStock
    ): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            priceInEur = price,
            stock = stock
        )
    }

    private fun createProductResponse(
        id: Long = 1L,
        name: String = productName,
        description: String = productDescription,
        price: BigDecimal = productPrice,
        stock: Long = productStock
    ): GetProductResponse {
        return GetProductResponse(
            id = id,
            name = name,
            description = description,
            priceInEur = price,
            stock = stock
        )
    }

    private fun createUpdateProductRequest(
        name: String? = null,
        description: String? = null,
        price: BigDecimal? = null,
        stock: Long? = null
    ): UpdateProductRequest {
        return UpdateProductRequest(
            name = name,
            description = description,
            priceInEur = price,
            stock = stock
        )
    }
}