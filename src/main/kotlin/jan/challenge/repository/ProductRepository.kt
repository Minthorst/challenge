package jan.challenge.repository

import jan.challenge.model.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {

    fun findProductById(id: Long): Product?
}