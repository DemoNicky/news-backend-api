package com.dobudobu.newsapiapp.Service.Impl;

import com.dobudobu.newsapiapp.Dto.Request.CommentRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.Comment;
import com.dobudobu.newsapiapp.Entity.User;
import com.dobudobu.newsapiapp.Repository.ArticlesRepository;
import com.dobudobu.newsapiapp.Repository.CommentRepository;
import com.dobudobu.newsapiapp.Repository.UserRepository;
import com.dobudobu.newsapiapp.Service.CommentService;
import com.dobudobu.newsapiapp.Util.AuthUtils.AuthenticationEmailUtil;
import com.dobudobu.newsapiapp.Util.GeneratorUtils.UUIDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UUIDGeneratorUtil uuidGeneratorUtil;

    @Autowired
    private AuthenticationEmailUtil authenticationEmailUtil;

    @Override
    public ResponseHandling commentArticle(String code, CommentRequest commentRequest) {
        ResponseHandling responseHandling = new ResponseHandling();

        Optional<Articles> articles = articlesRepository.findByArticlesCode(code);
        if (!articles.isPresent()){
            responseHandling.setMessage("article not found");
            responseHandling.setErrors(true);
            return responseHandling;
        }
        Optional<User> user = userRepository.findByEmail(authenticationEmailUtil.getEmailAuthentication());
        if (!user.isPresent()) {
            responseHandling.setMessage("User not found");
            responseHandling.setErrors(true);
            return responseHandling;
        }

        Comment comment = new Comment();
        comment.setCommentCode(getUniqueUUID());
        comment.setCommentContent(commentRequest.getCommentContent());
        comment.setCommentDatePost(new Date());
        comment.setCommentLike(0);
        comment.setCommentDissLike(0);
        comment.setUser(user.get());
        comment.setArticle(articles.get());

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        commentRepository.save(comment);
        articles.get().setComments(comments);
        articlesRepository.save(articles.get());

        responseHandling.setMessage("successfuly post comment");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    private String getUniqueUUID(){
        boolean loop = true;
        String ress = "";
        while (loop){
            String uuid = uuidGeneratorUtil.getUUIDCode();
            Optional<Comment> comment = commentRepository.findByCommentCode(uuid);

            if (!comment.isPresent()){
                ress = uuid;
                loop = false;
            }
        }
        return ress;
    }
}
