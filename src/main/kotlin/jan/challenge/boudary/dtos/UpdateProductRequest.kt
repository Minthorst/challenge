package jan.challenge.boudary.dtos

import java.math.BigDecimal

class UpdateProductRequest(
    val name: String? = null,
    val description: String? = null,
    val priceInEur: BigDecimal? = null,
    val stock: Long? = null
)
