package com.dobudobu.newsapiapp.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_comment")
public class Comment {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    //comment content digunakan untuk menyimpan konten dari komen
    @Column(name = "comment_content", length = 1000)
    private String commentContent;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "comment_date_post")
    private Date commentDatePost;

    @Column(name = "comment_like")
    private Integer commentLike;

    @Column(name = "comment_disslike")
    private Integer commentDissLike;

    @ManyToOne
    private User user;

    @ManyToOne
    private Articles articles;

    @OneToMany
    private List<CommentReply> commentReplies;

}