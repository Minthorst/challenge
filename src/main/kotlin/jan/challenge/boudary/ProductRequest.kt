package jan.challenge.boudary

import jan.challenge.model.Product
import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val description: String,
    val priceInEur: BigDecimal,
    val stock: Long
)

fun ProductRequest.toProduct(): Product {
    return Product(
        id = null,
        name = name,
        description = description,
        priceInEur = priceInEur,
        stock = stock
    )
}
