package com.dobudobu.newsapiapp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@Table(name = "tb_report_type")
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_name", length = 40, unique = true, nullable = false)
    private String reportName;

    @ManyToMany(mappedBy = "reportTypes")
    private List<CommentReport> commentReport;

    @ManyToMany(mappedBy = "reportTypes")
    private List<ArticleReport> articleReports;

}
