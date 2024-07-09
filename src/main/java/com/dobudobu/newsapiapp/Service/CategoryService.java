package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Response.CreateCategoryResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseHandling<CreateCategoryResponse> createCategory(String category);

}
