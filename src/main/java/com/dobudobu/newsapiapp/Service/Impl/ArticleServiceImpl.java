package com.dobudobu.newsapiapp.Service.Impl;

import com.cloudinary.Cloudinary;
import com.dobudobu.newsapiapp.Dto.Response.*;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.Category;
import com.dobudobu.newsapiapp.Entity.Image;
import com.dobudobu.newsapiapp.Entity.User;
import com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler.CustomDataAlreadyExistsException;
import com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler.CustomFailUploadImageException;
import com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler.CustomInternalServerErrorException;
import com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler.CustomNotFoundException;
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
import org.springframework.http.HttpStatus;
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
            throw new CustomDataAlreadyExistsException("content alrady exists");
        }

        // check is article title alrady exists
        Optional<Articles> existingArticleTitle = articlesRepository.findByArticlesTitle(articlesTitle);
        if (existingArticleTitle.isPresent()) {
            throw new CustomDataAlreadyExistsException("Article title alrady exists");
        }

        Optional<User> user = userRepository.findByEmail(authenticationEmailUtil.getEmailAuthentication());
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (!category.isPresent()){
            throw new CustomNotFoundException("category not found");
        }
        if (!user.isPresent()){
            throw new CustomNotFoundException("user not found");
        }

        UploadImageResult uploadImage;
        try {
            uploadImage = uploadImageUtil.uploadImage(image);
        } catch (IOException e) {
            throw new CustomFailUploadImageException("Failed to upload image: " + e.getMessage());
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
        articles.setActive(true);

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
        responseHandling.setHttpStatus(HttpStatus.OK);
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
            throw new CustomNotFoundException("article is empty/article not found");
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
        responseHandling.setHttpStatus(HttpStatus.OK);
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
            throw new CustomNotFoundException("article not found");
        }

        if (articles.get().getActive() == false || articles.get().equals(null) || articles.get().getActive() == null){
            throw new CustomNotFoundException("article not found");
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

        hitArticleDetailResponse.setCommentGetArticleResponses(commentGetArticleResponses);

        responseHandling.setData(hitArticleDetailResponse);
        responseHandling.setHttpStatus(HttpStatus.OK);
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
            throw new CustomNotFoundException("article not found");
        }

        if (!category.isPresent()){
            throw new CustomNotFoundException("category not found");
        }
        if (!user.isPresent()){
            throw new CustomNotFoundException("user not found");
        }

        //upload image
        UploadImageResult uploadImage;
        try {
            uploadImage = uploadImageUtil.uploadImage(image);
        } catch (IOException e) {
            throw new CustomFailUploadImageException("Failed to upload image: " + e.getMessage());
        }

        //delete image
        Map<?, ?> deleteResult = deleteImageUtil.deleteImage(articles.get().getImages().getPublicId());
        if (deleteResult.containsKey("error")){
            throw new CustomInternalServerErrorException("kesalahan saat menghapus gambar");
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
        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setMessage("success update article");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    @Override
    public ResponseHandling deleteArticle(String code) {
        ResponseHandling responseHandling = new ResponseHandling();
        Optional<Articles> articles = articlesRepository.findByArticlesCode(code);
        if (!articles.isPresent()) {
            throw new CustomNotFoundException("artile not found");
        }
        if (articles.get().getActive() == false || articles.get().getActive().equals(false)){
            throw new CustomNotFoundException("artile not found");
        }
        Articles articlesget = articles.get();
        articlesget.setActive(false);
        articlesRepository.save(articlesget);

        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setMessage("successfully deleted articles");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    @Override
    public ResponseHandling activatedArticle(String code) {
        ResponseHandling responseHandling = new ResponseHandling();
        Optional<Articles> articles = articlesRepository.findByArticlesCode(code);
        if (!articles.isPresent()) {
            throw new CustomNotFoundException("artile not found");
        }
        if (articles.get().getActive() == true || articles.get().getActive().equals(true)){
            throw new CustomDataAlreadyExistsException("article already activated");
        }
        Articles articlesget = articles.get();
        articlesget.setActive(true);
        articlesRepository.save(articlesget);

        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setMessage("successfully activated articles");
        responseHandling.setErrors(false);
        return responseHandling;
    }

    @Override
    public ResponseHandling<List<SearchArticleResponse>> searchArticle(String keyword, Integer page) {
        ResponseHandling<List<SearchArticleResponse>> responseHandling = new ResponseHandling<>();

        Pageable pageable = PageRequest.of(page, 10);
        Page<Articles> articles = articlesRepository.searchArticleByKeyword(keyword, pageable);

        List<SearchArticleResponse> responses = articles.stream().map((x)-> {
            SearchArticleResponse searchArticleResponse = new SearchArticleResponse();
            searchArticleResponse.setArticleCode(x.getArticlesCode());
            searchArticleResponse.setArticlesTitle(x.getArticlesTitle());
            searchArticleResponse.setCategory(x.getCategory().getCategoryName());
            searchArticleResponse.setDatePostedArticle(x.getDatePostedArticle());
            searchArticleResponse.setReadership(x.getReadership());
            searchArticleResponse.setLikes(x.getLikes());
            searchArticleResponse.setAuthor(x.getAuthor());
            searchArticleResponse.setImage(x.getImages().getUrlImage());

            return searchArticleResponse;
        }).collect(Collectors.toList());

        responseHandling.setData(responses);
        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setMessage("success search article");
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
