package com.dobudobu.newsapiapp.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReplyRequest {

    @NotBlank
    private String commentContent;

}
