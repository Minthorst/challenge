package jan.challenge

import org.springframework.data.repository.CrudRepository

interface ProductRepository: CrudRepository<Product, Long>{

    fun findProductById(id: Long): Product?
}