package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {

}
