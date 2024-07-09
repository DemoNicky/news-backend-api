package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByCategoryName(String category);
}