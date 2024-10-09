package jan.challenge.model

import jakarta.persistence.*
import jan.challenge.boudary.GetProductResponse
import java.math.BigDecimal


@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val description: String,
    val priceInEur: BigDecimal,
    val stock: Long
)

fun Product.toGetProductResponse(): GetProductResponse {
    return GetProductResponse(
        id = id!!,
        name = name,
        description = description,
        priceInEur = priceInEur,
        stock = stock
    )
}

