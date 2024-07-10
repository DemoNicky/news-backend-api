package com.dobudobu.newsapiapp.Dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentreplyGetArticleResponse {

    private String userCode;

    private String fullname;

    private String imageUrl;

    private String replyCommentCode;

    private String commentContent;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date commentDatePost;

    private Integer commentLike;

    private Integer commentDissLike;

}
