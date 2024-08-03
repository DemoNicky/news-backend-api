package com.dobudobu.newsapiapp.Dto.Response;

import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.ReportType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportArticleGetResponse {

    private String id;

    private String note;

    private List<ArticleReportTypeResponse> reportTypes;

    private GetArticleResponse articles;

}
