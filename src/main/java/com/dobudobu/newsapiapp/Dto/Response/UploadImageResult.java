package com.dobudobu.newsapiapp.Dto.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadImageResult {

    private String imageUrl;

    private String publicId;

}
