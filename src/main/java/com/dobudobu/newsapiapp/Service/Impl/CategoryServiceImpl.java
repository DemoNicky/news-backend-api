package com.dobudobu.newsapiapp.Service.Impl;

import com.dobudobu.newsapiapp.Dto.Response.CreateCategoryResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Entity.Category;
import com.dobudobu.newsapiapp.Repository.CategoryRepository;
import com.dobudobu.newsapiapp.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseHandling<CreateCategoryResponse> createCategory(String category) {
        ResponseHandling<CreateCategoryResponse> responseHandling = new ResponseHandling<>();
        Optional<Category> categoryCheck = categoryRepository.findByCategoryName(category);
        if (categoryCheck.isPresent()){
            responseHandling.setErrors(true);
            responseHandling.setMessage("duplicate category");
            return responseHandling;
        }
        Category categoryInsert = new Category();
        categoryInsert.setCategoryName(category);
        categoryRepository.save(categoryInsert);
        CreateCategoryResponse createCategory = new CreateCategoryResponse();
        createCategory.setCategoryName(category);
        responseHandling.setData(createCategory);
        responseHandling.setMessage("success insert data");
        responseHandling.setErrors(false);

        return responseHandling;
    }
}
