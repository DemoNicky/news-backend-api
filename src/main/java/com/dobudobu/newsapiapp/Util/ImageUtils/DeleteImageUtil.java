package com.dobudobu.newsapiapp.Util.ImageUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeleteImageUtil {

    private final Cloudinary cloudinary;

    public Map deleteImage(String publicId) throws IOException {
        Map<?, ?> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return deleteResult;
    }
}
