package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, String> {

}
