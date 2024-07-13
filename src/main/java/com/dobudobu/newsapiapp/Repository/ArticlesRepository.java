package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Articles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, String> {

    Optional<Articles> findByArticlesCode(String uuid);

    Page<Articles> findAll(Pageable pageable);

    Optional<Articles> findByContent(String content);

    Optional<Articles> findByArticlesTitle(String articlesTitle);
}
