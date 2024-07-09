package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, String> {

    Optional<Articles> findByArticlesCode(String uuid);
}
