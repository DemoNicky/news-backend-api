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

    @Column(name = "note", length = 400)
    private String note;

    @ManyToMany
    @JoinTable(
            name = "tb_comment_report_report_type",
            joinColumns = @JoinColumn(name = "comment_report_id"),
            inverseJoinColumns = @JoinColumn(name = "report_type_id")
    )
    private List<ReportType> reportTypes;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

}
