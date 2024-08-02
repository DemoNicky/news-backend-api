package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.ArticleReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, String> {
}
