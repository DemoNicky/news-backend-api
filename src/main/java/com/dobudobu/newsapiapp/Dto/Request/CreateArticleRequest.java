package com.dobudobu.newsapiapp.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleRequest {

    @NotBlank
    private String articlesTitle;

    @NotBlank
    private String content;

    @NotBlank
    private String categoryId;

}
