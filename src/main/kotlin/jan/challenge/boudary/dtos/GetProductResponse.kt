package jan.challenge.boudary.dtos

import java.math.BigDecimal

data class GetProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val priceInEur: BigDecimal,
    val stock: Long
)
