package jan.challenge.exceptions

class ProductNotFoundException(val productId: Long) : Exception("product with id $productId was not found")