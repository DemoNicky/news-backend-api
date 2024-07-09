package com.dobudobu.newsapiapp.Config.CloudinaryConfig;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Value("${configuration.cloudinary.cloud-name}")
    private String CLOUD_NAME;

    @Value("${configuration.cloudinary.cloud-api-key}")
    private String API_KEY;

    @Value("${configuration.cloudinary.cloud-api-secret}")
    private String API_SECRET;

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET ));
    }

}
