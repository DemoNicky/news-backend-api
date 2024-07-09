package com.dobudobu.newsapiapp.Controller;

import com.dobudobu.newsapiapp.Dto.Request.CreateArticleRequest;
import com.dobudobu.newsapiapp.Dto.Response.CreateArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(
            path = "/create-article"
    )
    private ResponseEntity<ResponseHandling<CreateArticleResponse>> createArticle(@RequestParam("image") MultipartFile image,
                                                                                  @RequestParam("title")String articlesTitle,
                                                                                  @RequestParam("content")String content,
                                                                                  @RequestParam("categoryId")String categoryId) throws IOException {
        ResponseHandling<CreateArticleResponse> responseHandling = articleService.createArticle(image, articlesTitle, content, categoryId);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

}
