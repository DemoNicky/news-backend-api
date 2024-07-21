package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Optional<Comment> findByCommentCode(String uuid);
}
