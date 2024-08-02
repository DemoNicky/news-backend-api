package com.dobudobu.newsapiapp.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportArticleRequest {

    @NotBlank
    private List<Long> reportId;

    @NotBlank
    @Size(max = 400)
    private String note;
}
