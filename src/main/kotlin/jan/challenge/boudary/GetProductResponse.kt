package jan.challenge.boudary

import java.math.BigDecimal

class GetProductResponse (
    val id: Long,
    val name: String,
    val description: String,
    val priceInEur: BigDecimal,
    val stock: Long
    )
