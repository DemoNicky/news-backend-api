package com.dobudobu.newsapiapp.Dto.Response;

import com.dobudobu.newsapiapp.Entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UpdateArticleResponse {

    private String articlesCode;

    private String articlesTitle;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date datePostedArticle;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateUpdateArticle;

    private Integer readership;

    private Integer likes;

    private String author;

    private String image;

    private String content;

    @JsonIgnore
    private Category category;

}
