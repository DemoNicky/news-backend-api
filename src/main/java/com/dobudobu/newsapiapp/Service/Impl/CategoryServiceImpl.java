package com.dobudobu.newsapiapp.Service.Impl;

import com.dobudobu.newsapiapp.Dto.Response.CreateCategoryResponse;
import com.dobudobu.newsapiapp.Dto.Response.GetArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.Category;
import com.dobudobu.newsapiapp.Repository.ArticlesRepository;
import com.dobudobu.newsapiapp.Repository.CategoryRepository;
import com.dobudobu.newsapiapp.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

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

    @Override
    public ResponseHandling<List<GetArticleResponse>> getArticleByCategory(Long categoryId, Integer page) {
        ResponseHandling<List<GetArticleResponse>> responseHandling = new ResponseHandling<>();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            responseHandling.setMessage("category not found");
            responseHandling.setErrors(true);
            return responseHandling;
        }
        Pageable pageable = PageRequest.of(page, 10);
        Page<Articles> articles = articlesRepository.findArticlesByCategory(category, pageable);

        List<GetArticleResponse> result = articles.stream().map((p)->{
            GetArticleResponse getArticleResponse = new GetArticleResponse();
            getArticleResponse.setArticleCode(p.getArticlesCode());
            getArticleResponse.setArticlesTitle(p.getArticlesTitle());
            getArticleResponse.setCategory(p.getCategory().getCategoryName());
            getArticleResponse.setDatePostedArticle(p.getDatePostedArticle());
            getArticleResponse.setReadership(p.getReadership());
            getArticleResponse.setLikes(p.getLikes());
            getArticleResponse.setAuthor(p.getAuthor());
            getArticleResponse.setImage(p.getImages().getUrlImage());
            return getArticleResponse;
        }).collect(Collectors.toList());

        responseHandling.setData(result);
        responseHandling.setMessage("success get data");
        responseHandling.setErrors(false);

        return responseHandling;
    }
}
