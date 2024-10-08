package jan.challenge.boudary

import jan.challenge.Product
import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val description: String,
    val priceInEur: BigDecimal,
    val stock: Long
)

fun CreateProductRequest.toProduct(): Product {
    return Product(
        id = null,
        name = name,
        description = description,
        priceInEur = priceInEur,
        stock = stock
    )
}
