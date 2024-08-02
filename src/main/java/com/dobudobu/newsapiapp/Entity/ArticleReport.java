package com.dobudobu.newsapiapp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "tb_article_report")
@Data
public class ArticleReport {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "note", length = 400)
    private String note;

    @ManyToMany
    @JoinTable(
            name = "tb_article_report_report_Type",
            joinColumns = @JoinColumn(name = "article_report_id"),
            inverseJoinColumns = @JoinColumn(name = "report_type_id")
    )
    private List<ReportType> reportTypes;

    @ManyToOne
    @JoinColumn(name = "articles_id", nullable = false)
    private Articles articles;
}
