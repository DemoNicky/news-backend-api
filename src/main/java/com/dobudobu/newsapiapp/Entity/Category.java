package com.dobudobu.newsapiapp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@Table(name = "tb_category")
public class Category {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "category", length = 20, unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Articles> articlesList;

}
