package com.dobudobu.newsapiapp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "tb_report_type")
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_name", length = 40, unique = true, nullable = false)
    private String reportName;

    @ManyToOne
    @JoinColumn(name = "id_comment_report")
    private CommentReport commentReport;

    @ManyToOne
    @JoinColumn(name = "id_article_report")
    private ArticleReport articleReport;

}
