package com.dobudobu.newsapiapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_article")
public class Articles {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "articles_code", length = 10, unique = true, nullable = false)
    private String articlesCode;

    @Column(name = "articles_title", length = 40, unique = true, nullable = false)
    private String articlesTitle;

    @Column(name = "date_posted_article")
    private Date datePostedArticle;

    @Column(name = "date_update_article")
    private Date dateUpdateArticle;

    //ini untuk jumlah viewers
    @Column(name = "readership")
    private Integer readership;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "author")
    private String author;

    @Column(name = "content", length = 40000, unique = true, nullable = false)
    private String content;

    @Column(name = "active")
    private Boolean Active;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    private Image images;

    @ManyToMany

    private List<ArticleReport> articleReports;

}
