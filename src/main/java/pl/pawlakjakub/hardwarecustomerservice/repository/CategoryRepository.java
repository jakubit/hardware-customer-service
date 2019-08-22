package pl.pawlakjakub.hardwarecustomerservice.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawlakjakub.hardwarecustomerservice.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
