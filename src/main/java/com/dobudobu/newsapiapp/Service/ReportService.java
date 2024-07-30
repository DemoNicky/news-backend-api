package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;

public interface ReportService {
    ResponseHandling createReportType(ReportTypeRequest reportTypeRequest);
}
