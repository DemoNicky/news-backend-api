package com.dobudobu.newsapiapp.Util.ImageUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dobudobu.newsapiapp.Dto.Response.UploadImageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UploadImageUtil {

    private final Cloudinary cloudinary;

    private static final long MAX_FILE_SIZE_BYTES = 9 * 1024 * 1024; // 9 MB

    public UploadImageResult uploadImage(MultipartFile image) throws IOException {

        String contentType = image.getContentType();
        //validate image extention
        if (!isImage(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
        }

        // Validate file size
        if (image.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException("File size exceeds the maximum allowed limit of 9 MB.");
        }

        Map<?, ?> result = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = result.get("url").toString();
        String publicId = result.get("public_id").toString();

        UploadImageResult uploadResult = UploadImageResult.builder()
                .imageUrl(imageUrl)
                .publicId(publicId)
                .build();
        return uploadResult;
    }

    private boolean isImage(String contentType) {
        return contentType != null && (contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif"));
    }

}
