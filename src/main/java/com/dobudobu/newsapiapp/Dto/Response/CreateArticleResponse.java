package com.dobudobu.newsapiapp.Dto.Response;

import com.dobudobu.newsapiapp.Entity.Category;
import com.dobudobu.newsapiapp.Entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CreateArticleResponse {

    private String articlesCode;

    private String articlesTitle;

    private Date datePostedArticle;

    private Integer readership;

    private Integer likes;

    private String author;

    private String content;

    private Category category;

}
