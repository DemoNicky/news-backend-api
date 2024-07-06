package com.dobudobu.newsapiapp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_article")
public class Articles {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "articles_title", length = 40, unique = true)
    private String articlesTitle;

    @Column(name = "date_posted_article")
    private Date datePostedArticle;

    //ini untuk jumlah viewers
    @Column(name = "readership")
    private Long readership;

    @Column(name = "like")
    private Integer like;

    @Column(name = "author")
    private String author;

    @Column(name = "content", length = 5000, unique = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
