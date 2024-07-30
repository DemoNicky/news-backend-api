package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {
}
