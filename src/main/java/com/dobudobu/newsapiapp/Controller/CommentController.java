package com.dobudobu.newsapiapp.Controller;

import com.dobudobu.newsapiapp.Dto.Request.CommentRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(
            path = "/comment-article/{news-code}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling> commentArticle(@PathVariable("news-code")String code,
                                                           @RequestBody CommentRequest commentRequest){
        ResponseHandling responseHandling = commentService.commentArticle(code, commentRequest);
        if (responseHandling.getErrors().equals(true)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

}
