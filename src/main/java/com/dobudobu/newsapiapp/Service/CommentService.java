package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Request.CommentReplyRequest;
import com.dobudobu.newsapiapp.Dto.Request.CommentRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;

public interface CommentService {

    ResponseHandling commentArticle(String code, CommentRequest commentRequest);

    ResponseHandling commentReplyArticle(String code, String commentCode, CommentReplyRequest commentReplyRequest);
}
