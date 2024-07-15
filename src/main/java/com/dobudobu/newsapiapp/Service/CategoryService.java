package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Response.CreateCategoryResponse;
import com.dobudobu.newsapiapp.Dto.Response.GetArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseHandling<CreateCategoryResponse> createCategory(String category);

    ResponseHandling<List<GetArticleResponse>> getArticleByCategory(Long categoryId, Integer page);
}
