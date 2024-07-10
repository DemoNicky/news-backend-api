package com.dobudobu.newsapiapp.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dobudobu.newsapiapp.Dto.Response.*;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.Category;
import com.dobudobu.newsapiapp.Entity.Image;
import com.dobudobu.newsapiapp.Entity.User;
import com.dobudobu.newsapiapp.Repository.ArticlesRepository;
import com.dobudobu.newsapiapp.Repository.CategoryRepository;
import com.dobudobu.newsapiapp.Repository.ImageRepository;
import com.dobudobu.newsapiapp.Repository.UserRepository;
import com.dobudobu.newsapiapp.Service.ArticleService;
import com.dobudobu.newsapiapp.Util.AuthenticationEmailUtil;
import com.dobudobu.newsapiapp.Util.UUIDGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthenticationEmailUtil authenticationEmailUtil;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UUIDGeneratorUtil uuidGeneratorUtil;

    private final Cloudinary cloudinary;

    @Override
    @Transactional
    public ResponseHandling<CreateArticleResponse> createArticle(MultipartFile image, String articlesTitle, String content, Long categoryId) throws IOException {
        ResponseHandling<CreateArticleResponse> responseHandling = new ResponseHandling<>();
        Optional<User> user = userRepository.findByEmail(authenticationEmailUtil.getEmailAuthentication());
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (!category.isPresent()){
            responseHandling.setErrors(true);
            responseHandling.setMessage("category not found");
            return responseHandling;
        }
        if (!user.isPresent()){
            responseHandling.setErrors(true);
            responseHandling.setMessage("user not found");
            return responseHandling;
        }
        Map<?, ?> result = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = result.get("url").toString();

        Image imageSave = new Image();
        imageSave.setUrlImage(imageUrl);

        imageRepository.save(imageSave);

        Articles articles = new Articles();
        articles.setArticlesCode(getUniqueUUID());
        articles.setArticlesTitle(articlesTitle);
        articles.setDatePostedArticle(new Date());
        articles.setReadership(0);
        articles.setLikes(0);
        articles.setAuthor(user.get().getFullname());
        articles.setContent(content);
        articles.setCategory(category.get());
        articles.setUser(user.get());
        articles.setImages(imageSave);

        articlesRepository.save(articles);

        CreateArticleResponse createArticleResponse = CreateArticleResponse.builder()
                .articlesCode(articles.getArticlesCode())
                .articlesTitle(articles.getArticlesTitle())
                .datePostedArticle(articles.getDatePostedArticle())
                .readership(articles.getReadership())
                .readership(articles.getReadership())
                .likes(articles.getLikes())
                .author(articles.getAuthor())
                .image(articles.getImages().getUrlImage())
                .content(articles.getContent())
                .category(articles.getCategory())
                .build();

        responseHandling.setData(createArticleResponse);
        responseHandling.setMessage("success upload article");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    @Override
    public ResponseHandling<List<GetArticleResponse>> getArticle(Integer page) {
        ResponseHandling<List<GetArticleResponse>> responseHandling = new ResponseHandling<>();

        Pageable pageable = PageRequest.of(page, 10);
        Page<Articles> articles = articlesRepository.findAll(pageable);
        if (articles.isEmpty()){
            responseHandling.setMessage("article is empty/article not found");
            responseHandling.setErrors(true);
            return responseHandling;
        }
        List<GetArticleResponse> getArticleResponses = articles.stream().map((p)->{
            GetArticleResponse getArticleResponse = new GetArticleResponse();
            getArticleResponse.setArticleCode(p.getArticlesCode());
            getArticleResponse.setArticlesTitle(p.getArticlesTitle());
            getArticleResponse.setCategory(p.getCategory().getCategoryName());
            getArticleResponse.setDatePostedArticle(p.getDatePostedArticle());
            getArticleResponse.setReadership(p.getReadership());
            getArticleResponse.setLikes(p.getLikes());
            getArticleResponse.setAuthor(p.getUser().getFullname());
            getArticleResponse.setImage(p.getImages().getUrlImage());

            return getArticleResponse;
        }).collect(Collectors.toList());

        responseHandling.setData(getArticleResponses);
        responseHandling.setMessage("success get article data");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    @Override
    @Transactional
    public ResponseHandling<HitArticleDetailResponse> hitArticleDetail(String articleCode) {
        ResponseHandling<HitArticleDetailResponse> responseHandling = new ResponseHandling<>();
        Optional<Articles> articles = articlesRepository.findByArticlesCode(articleCode);
        if (!articles.isPresent()){
            responseHandling.setMessage("articles not found");
            responseHandling.setErrors(true);
            return responseHandling;
        }
        Articles articlesData = articles.get();
        articlesData.setReadership(articlesData.getReadership() + 1);
        articlesRepository.save(articlesData);

        HitArticleDetailResponse hitArticleDetailResponse = new HitArticleDetailResponse();
        hitArticleDetailResponse.setArticleCode(articlesData.getArticlesCode());
        hitArticleDetailResponse.setArticlesTitle(articlesData.getArticlesTitle());
        hitArticleDetailResponse.setCategory(articlesData.getCategory().getCategoryName());
        hitArticleDetailResponse.setDatePostedArticle(articlesData.getDatePostedArticle());
        hitArticleDetailResponse.setReadership(articlesData.getReadership());
        hitArticleDetailResponse.setLikes(articlesData.getLikes());
        hitArticleDetailResponse.setAuthor(articlesData.getAuthor());
        hitArticleDetailResponse.setImage(articlesData.getImages().getUrlImage());
        hitArticleDetailResponse.setContent(articlesData.getContent());
        List<CommentGetArticleResponse> commentGetArticleResponses = articlesData.getComments().stream().map((comment)->{
            CommentGetArticleResponse commentGetArticleResponse = new CommentGetArticleResponse();
            commentGetArticleResponse.setCommentCode(comment.getCommentCode());
            commentGetArticleResponse.setUserCode(comment.getUser().getUserCode());
            commentGetArticleResponse.setFullname(comment.getUser().getFullname());
            commentGetArticleResponse.setImageUrl(comment.getUser().getImage().getUrlImage());
            commentGetArticleResponse.setCommentContent(comment.getCommentContent());
            commentGetArticleResponse.setCommentDatePost(comment.getCommentDatePost());
            commentGetArticleResponse.setCommentLike(comment.getCommentLike());
            commentGetArticleResponse.setCommentDissLike(comment.getCommentDissLike());
            List<CommentreplyGetArticleResponse> commentreplyGetArticleResponses = comment.getCommentReplies().stream().map((reply)->{
                CommentreplyGetArticleResponse commentreplyGetArticleResponse = new CommentreplyGetArticleResponse();
                commentreplyGetArticleResponse.setUserCode(reply.getUser().getUserCode());
                commentreplyGetArticleResponse.setFullname(reply.getUser().getFullname());
                commentreplyGetArticleResponse.setImageUrl(reply.getUser().getImage().getUrlImage());
                commentreplyGetArticleResponse.setReplyCommentCode(reply.getReplyCommentCode());
                commentreplyGetArticleResponse.setCommentContent(reply.getCommentContent());
                commentreplyGetArticleResponse.setCommentDatePost(reply.getCommentDatePost());
                commentreplyGetArticleResponse.setCommentLike(reply.getCommentLike());
                commentreplyGetArticleResponse.setCommentDissLike(reply.getCommentDissLike());

                return commentreplyGetArticleResponse;
            }).collect(Collectors.toList());

            commentGetArticleResponse.setCommentreplyGetArticleResponses(commentreplyGetArticleResponses);
            return commentGetArticleResponse;
        }).collect(Collectors.toList());

        responseHandling.setData(hitArticleDetailResponse);
        responseHandling.setMessage("success hit article detail data");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    private String getUniqueUUID(){
        boolean loop = true;
        String ress = "";
        while (loop){
            String uuid = uuidGeneratorUtil.getUUIDCode();
            Optional<Articles> articles = articlesRepository.findByArticlesCode(uuid);
            if (!articles.isPresent()){
                ress = uuid;
                loop = false;
            }
        }
        return ress;
    }
}
