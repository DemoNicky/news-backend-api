package com.dobudobu.newsapiapp.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dobudobu.newsapiapp.Dto.Response.CreateArticleResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.Category;
import com.dobudobu.newsapiapp.Entity.User;
import com.dobudobu.newsapiapp.Repository.ArticlesRepository;
import com.dobudobu.newsapiapp.Repository.CategoryRepository;
import com.dobudobu.newsapiapp.Repository.UserRepository;
import com.dobudobu.newsapiapp.Service.ArticleService;
import com.dobudobu.newsapiapp.Util.AuthenticationEmailUtil;
import com.dobudobu.newsapiapp.Util.UUIDGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
    private UUIDGeneratorUtil uuidGeneratorUtil;

    private final Cloudinary cloudinary;

    @Override
    @Async
    @Transactional
    public ResponseHandling<CreateArticleResponse> createArticle(MultipartFile image, String articlesTitle, String content, String categoryId) throws IOException {
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
        CreateArticleResponse createArticleResponse = CreateArticleResponse.builder()
                .articlesCode(articles.getArticlesCode())
                .articlesTitle(articles.getArticlesTitle())
                .datePostedArticle(articles.getDatePostedArticle())
                .readership(articles.getReadership())
                .readership(articles.getReadership())
                .likes(articles.getLikes())
                .author(articles.getAuthor())
                .content(articles.getContent())
                .category(articles.getCategory())
                .build();

        responseHandling.setData(createArticleResponse);
        responseHandling.setMessage("success upload article");
        responseHandling.setErrors(false);
        return null;
    }

    private String getUniqueUUID(){
        boolean loop = true;
        String ress = "";
        while (loop){
            String uuid = uuidGeneratorUtil.getUUIDCode();
            Optional<Articles> articles = articlesRepository.findByArticlesCode(uuid);
            if (!articles.isPresent()){
                ress = uuid;
            }
        }
        return ress;
    }
}
