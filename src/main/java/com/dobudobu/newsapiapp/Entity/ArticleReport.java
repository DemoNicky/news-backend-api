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
    @GeneratedValue(generator = "uuiud")
    private String id;

    @Column(name = "note", length = 400)
    private String note;

    @OneToMany(mappedBy = "articleReport", cascade = CascadeType.ALL)
    private List<ReportType> reportTypes;

    @ManyToOne
    @JoinColumn(name = "articles_id", nullable = false)
    private Articles articles;
}
