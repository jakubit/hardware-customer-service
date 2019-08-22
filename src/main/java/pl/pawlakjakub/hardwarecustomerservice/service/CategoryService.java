package pl.pawlakjakub.hardwarecustomerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.pawlakjakub.hardwarecustomerservice.model.Category;
import pl.pawlakjakub.hardwarecustomerservice.repository.CategoryRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Secured("ROLE_USER")
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category;
    }


    public List<Category> getAllCategories() {
        List<Category> categories = new LinkedList<>();
        categoryRepository.findAll().forEach(categories::add);

        return categories;
    }
}
