package api.exam.io.read.info.domain.category.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository {

    Optional<Category> findByName(final String name);
}
