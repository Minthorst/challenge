package jan.challenge.boundary

import com.fasterxml.jackson.databind.ObjectMapper
import jan.challenge.ProductService
import jan.challenge.boudary.GetProductResponse
import jan.challenge.boudary.ProductController
import jan.challenge.boudary.ProductRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal

class ProductControllerTest {
    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()


    @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var productController: ProductController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build()
    }

    @Test
    fun `saveProduct should return 200 and the saved product`() {
        val productRequest = ProductRequest(
            name = "Test Product",
            description = "Test description",
            priceInEur = BigDecimal(10),
            stock = 100
        )

        val productResponse = GetProductResponse(
            id = 1L,
            name = "Test Product",
            description = "Test description",
            priceInEur = BigDecimal(10),
            stock = 100
        )

        whenever(productService.saveProduct(productRequest)).thenReturn(productResponse)

        mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest))
        )
            .andExpect(status().isOk)
        content().json(
            objectMapper.writeValueAsString(productResponse)
        )

        verify(productService).saveProduct(any())
    }

    @Test
    fun `getProductById should return 200 and the product`() {
        val productId = 1L
        val productResponse = GetProductResponse(productId, "Test Product", "Test description", BigDecimal(10), 100)

        whenever(productService.findProductById(productId)).thenReturn(productResponse)

        mockMvc.perform(
            get("/products/{id}", productId)
        )
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    objectMapper.writeValueAsString(productResponse)
                )
            )

        verify(productService).findProductById(productId)
    }
}