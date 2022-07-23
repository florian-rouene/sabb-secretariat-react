package fr.sabb.controller;

import fr.sabb.data.object.SubCategory;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.subcategory.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/sub_categories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public List<SubCategory> getSubCategorys() {
        return subCategoryService.getAll();
    }

    @GetMapping("/{id}")
    public SubCategory getCategory(@PathVariable int id) {
        return subCategoryService.getById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable int id, @RequestBody SubCategory subCategory) throws ValidationException {
        SubCategory currentCategory = subCategoryService.getById(id).orElseThrow(RuntimeException::new);
        currentCategory.setName(subCategory.getName());
        currentCategory.setSex(subCategory.getSex());
        subCategoryService.updateOrInsert(subCategory);

        return ResponseEntity.ok(currentCategory);
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody SubCategory subCategory) throws URISyntaxException, ValidationException {
        subCategoryService.updateOrInsert(subCategory);
        return ResponseEntity.created(new URI("/sub_categories/" + subCategory.getId())).body(subCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable int id) {
        SubCategory subCategoryToDelete = new SubCategory();
        subCategoryToDelete.setId(id);
        subCategoryService.delete(subCategoryToDelete);
        return ResponseEntity.ok().build();
    }
}
