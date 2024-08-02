package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.Request.ReportArticleRequest;
import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ReportTypeGetResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;

import java.util.List;

public interface ReportService {
    ResponseHandling createReportType(ReportTypeRequest reportTypeRequest);


    ResponseHandling reportArticle(String articlesCode, ReportArticleRequest reportArticleRequest);

    ResponseHandling<List<ReportTypeGetResponse>> getReportType();
}
