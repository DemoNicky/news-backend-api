package com.dobudobu.newsapiapp.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCommentRequest {

    @NotBlank
    private String commentCode;

    @NotBlank
    private String reportTypeid;

}
