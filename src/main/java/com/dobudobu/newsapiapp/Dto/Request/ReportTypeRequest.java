package com.dobudobu.newsapiapp.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportTypeRequest {

    @NotBlank
    private String reportType;

}
