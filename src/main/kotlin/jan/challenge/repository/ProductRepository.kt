package jan.challenge.repository

import jan.challenge.model.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<Product, Long> {

    fun findProductById(id: Long): Product?
}