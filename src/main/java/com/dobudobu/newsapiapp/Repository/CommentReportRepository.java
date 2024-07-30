package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, String > {

}
