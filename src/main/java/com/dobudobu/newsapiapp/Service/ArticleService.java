package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Response.CreateArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleService {
    ResponseHandling<CreateArticleResponse> createArticle(MultipartFile image, String articlesTitle, String content, String categoryId) throws IOException;
}
