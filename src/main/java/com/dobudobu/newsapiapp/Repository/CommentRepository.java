package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Optional<Comment> findByCommentCode(String uuid);

    @Query("SELECT c FROM Comment c WHERE c.commentCode = :commentCode AND c.article = :article")
    Optional<Comment> findByCommentCodeAndByArticle(@Param("commentCode") String commentCode, @Param("article") Articles articles);

}
