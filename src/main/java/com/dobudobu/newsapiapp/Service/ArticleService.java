package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Response.CreateArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.GetArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.HitArticleDetailResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    ResponseHandling<CreateArticleResponse> createArticle(MultipartFile image, String articlesTitle, String content, Long categoryId) throws IOException;

    ResponseHandling<List<GetArticleResponse>> getArticle(Integer page);

    ResponseHandling<HitArticleDetailResponse> hitArticleDetail(String articleCode);
}
