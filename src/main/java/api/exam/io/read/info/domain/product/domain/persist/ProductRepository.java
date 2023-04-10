package api.exam.io.read.info.domain.product.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    Optional<Product> findByName(final String name);
}
