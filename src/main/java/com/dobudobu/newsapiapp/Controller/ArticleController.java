package com.dobudobu.newsapiapp.Controller;

import com.dobudobu.newsapiapp.Dto.Request.CommentRequest;
import com.dobudobu.newsapiapp.Dto.Response.*;
import com.dobudobu.newsapiapp.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

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
                                                                                  @RequestParam("categoryId")Long categoryId) throws IOException {
        ResponseHandling<CreateArticleResponse> responseHandling = articleService.createArticle(image, articlesTitle, content, categoryId);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PostMapping(
            path = "/activate-article/{article-code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling> activateArticle(@PathVariable("article-code")String code){
        ResponseHandling responseHandling = articleService.activatedArticle(code);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(
            path = "/get-article",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseHandling<List<GetArticleResponse>>> getArticle(@RequestParam(name = "page", required = true) Integer page){
        ResponseHandling<List<GetArticleResponse>> responseHandling = articleService.getArticle(page);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(
            path = "/hit-article-detail/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseHandling<HitArticleDetailResponse>> hitArticleDetail(@PathVariable(name = "code", required = true) String articleCode){
        ResponseHandling<HitArticleDetailResponse> responseHandling = articleService.hitArticleDetail(articleCode);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PutMapping(
            path = "/update-article/{article-code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseHandling<UpdateArticleResponse>>updateArticle(@RequestParam("image") MultipartFile image,
                                                                                 @RequestParam("title")String articlesTitle,
                                                                                 @RequestParam("content")String content,
                                                                                 @RequestParam("categoryId")Long categoryId,
                                                                                 @PathVariable(name = "article-code", required = true)String code) throws IOException {
        ResponseHandling<UpdateArticleResponse> responseHandling = articleService.articleUpdate(image, articlesTitle, content, categoryId, code);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @DeleteMapping(
            path = "/delete-article/{article-code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling> deleteArticle(@PathVariable("article-code")String code){
        ResponseHandling responseHandling = articleService.deleteArticle(code);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(
            path = "/search-article",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling<List<SearchArticleResponse>>> searchArticle(@RequestParam("keyword")String keyword,
                                                                                       @RequestParam("page")Integer page){
        ResponseHandling<List<SearchArticleResponse>> responseHandling = articleService.searchArticle(keyword, page);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

}
