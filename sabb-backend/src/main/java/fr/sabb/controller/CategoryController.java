package fr.sabb.controller;

import fr.sabb.data.object.Category;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getCategorys() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id) {
        return categoryService.getById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable int id, @RequestBody Category category) throws ValidationException {
        Category currentCategory = categoryService.getById(id).orElseThrow(RuntimeException::new);
        currentCategory.setName(category.getName());
        currentCategory.setAutobind(category.isAutobind());
        categoryService.updateOrInsert(category);

        return ResponseEntity.ok(currentCategory);
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody Category category) throws URISyntaxException, ValidationException {
        categoryService.updateOrInsert(category);
        return ResponseEntity.created(new URI("/categories/" + category.getId())).body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable int id) {
        Category categoryToDelete = new Category();
        categoryToDelete.setId(id);
        categoryService.delete(categoryToDelete);
        return ResponseEntity.ok().build();
    }
}
