package com.dobudobu.newsapiapp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@Table(name = "tb_comment_report")
public class CommentReport {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuiud")
    private String id;

    @Column(name = "column", length = 400)
    private String note;

    @OneToMany(mappedBy = "commentReport", cascade = CascadeType.ALL)
    private List<ReportType> reportTypes;

    @ManyToOne
    @JoinColumn(name = "articles_id", nullable = false)
    private Articles articles;

}
