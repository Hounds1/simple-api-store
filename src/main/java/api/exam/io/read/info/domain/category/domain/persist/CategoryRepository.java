package api.exam.io.read.info.domain.category.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository {
}
