package com.dobudobu.newsapiapp.Dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchArticleResponse {

    private String articleCode;

    private String articlesTitle;

    private String category;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date datePostedArticle;

    private Integer readership;

    private Integer likes;

    private String author;

    private String image;

}
