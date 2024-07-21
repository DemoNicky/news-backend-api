package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Articles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, String> {

    Optional<Articles> findByArticlesCode(String uuid);

    @Query("SELECT x FROM Articles x WHERE x.Active = true")
    Page<Articles> findAll(Pageable pageable);

    Optional<Articles> findByContent(String content);

    Optional<Articles> findByArticlesTitle(String articlesTitle);

    @Query("SELECT p FROM Articles p WHERE p.category.id = :category")
    Page<Articles> findArticlesByCategory(@Param("category") Long category, Pageable pageable);

    @Query("SELECT a FROM Articles a WHERE a.articlesTitle LIKE %:keyword% OR a.content LIKE %:keyword%")
    Page<Articles> searchArticleByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
