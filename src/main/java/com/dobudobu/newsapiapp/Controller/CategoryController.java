package com.dobudobu.newsapiapp.Controller;

import com.dobudobu.newsapiapp.Dto.Response.CreateCategoryResponse;
import com.dobudobu.newsapiapp.Dto.Response.GetArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Service.CategoryService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(
            path = "/create-category",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling<CreateCategoryResponse>> createCategory(@RequestParam("category") @NotBlank String category){
        ResponseHandling<CreateCategoryResponse> response = categoryService.createCategory(category);
        if (response.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = "/get-article-by-category",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling<List<GetArticleResponse>>>getArticleByCategory(@RequestParam(name = "categoryId", required = true) Long categoryId,
                                                                                          @RequestParam(name = "page", required = true)Integer page){
        ResponseHandling<List<GetArticleResponse>> response = categoryService.getArticleByCategory(categoryId, page);
        if (response.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
