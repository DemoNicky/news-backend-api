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
import com.dobudobu.newsapiapp.Util.AuthUtils.AuthenticationEmailUtil;
import com.dobudobu.newsapiapp.Util.GeneratorUtils.UUIDGeneratorUtil;
import com.dobudobu.newsapiapp.Util.ImageUtils.DeleteImageUtil;
import com.dobudobu.newsapiapp.Util.ImageUtils.UploadImageUtil;
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

    @Autowired
    private UploadImageUtil uploadImageUtil;

    @Autowired
    private DeleteImageUtil deleteImageUtil;

    private final Cloudinary cloudinary;

    @Override
    @Transactional
    public ResponseHandling<CreateArticleResponse> createArticle(MultipartFile image, String articlesTitle, String content, Long categoryId) throws IOException {
        ResponseHandling<CreateArticleResponse> responseHandling = new ResponseHandling<>();

        // check is content alrady exists
        Optional<Articles> existingArticle = articlesRepository.findByContent(content);
        if (existingArticle.isPresent()) {
            responseHandling.setErrors(true);
            responseHandling.setMessage("Article content alrady exists");
            return responseHandling;
        }

        // check is article title alrady exists
        Optional<Articles> existingArticleTitle = articlesRepository.findByArticlesTitle(articlesTitle);
        if (existingArticleTitle.isPresent()) {
            responseHandling.setErrors(true);
            responseHandling.setMessage("Article title alrady exists");
            return responseHandling;
        }

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

        UploadImageResult uploadImage;
        try {
            uploadImage = uploadImageUtil.uploadImage(image);
        } catch (IOException e) {
            responseHandling.setErrors(true);
            responseHandling.setMessage("Failed to upload image: " + e.getMessage());
            return responseHandling;
        }

        Image imageSave = new Image();
        imageSave.setUrlImage(uploadImage.getImageUrl());
        imageSave.setPublicId(uploadImage.getPublicId());
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

    @Override
    @Transactional
    public ResponseHandling<UpdateArticleResponse> articleUpdate(MultipartFile image, String articlesTitle, String content, Long categoryId, String code) throws IOException {
        ResponseHandling<UpdateArticleResponse> responseHandling = new ResponseHandling<>();
        Optional<User> user = userRepository.findByEmail(authenticationEmailUtil.getEmailAuthentication());
        Optional<Category> category = categoryRepository.findById(categoryId);
        Optional<Articles> articles = articlesRepository.findByArticlesCode(code);

        if (!articles.isPresent()){
            responseHandling.setErrors(true);
            responseHandling.setMessage("articles not found");
            return responseHandling;
        }

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

        //upload image
        UploadImageResult uploadImage;
        try {
            uploadImage = uploadImageUtil.uploadImage(image);
        } catch (IOException e) {
            responseHandling.setErrors(true);
            responseHandling.setMessage("Failed to upload image: " + e.getMessage());
            return responseHandling;
        }

        //delete image
        Map<?, ?> deleteResult = deleteImageUtil.deleteImage(articles.get().getImages().getPublicId());
        if (deleteResult.containsKey("error")){
            String error = deleteResult.get("error").toString();
            responseHandling.setMessage(error);
            responseHandling.setErrors(true);
            return responseHandling;
        }

        Articles articlesget = articles.get();
        articlesget.setArticlesTitle(articlesTitle);
        articlesget.setContent(content);
        articlesget.setCategory(category.get());
        articlesget.setDateUpdateArticle(new Date());
        articlesget.getImages().setUrlImage(uploadImage.getImageUrl());
        articlesget.getImages().setPublicId(uploadImage.getPublicId());
        articlesRepository.save(articlesget);

        responseHandling.setData(UpdateArticleResponse.builder()
                .articlesCode(articlesget.getArticlesCode())
                .articlesTitle(articlesget.getArticlesTitle())
                .datePostedArticle(articlesget.getDatePostedArticle())
                .dateUpdateArticle(articlesget.getDateUpdateArticle())
                .readership(articlesget.getReadership())
                .readership(articlesget.getReadership())
                .likes(articlesget.getLikes())
                .author(articlesget.getAuthor())
                .image(articlesget.getImages().getUrlImage())
                .content(articlesget.getContent())
                .category(articlesget.getCategory())
                .build());
        responseHandling.setMessage("success update article");
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
