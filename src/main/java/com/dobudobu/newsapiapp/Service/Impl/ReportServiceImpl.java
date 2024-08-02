package com.dobudobu.newsapiapp.Service.Impl;

import com.dobudobu.newsapiapp.Dto.Request.ReportArticleRequest;
import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ReportTypeGetResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Entity.ArticleReport;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.ReportType;
import com.dobudobu.newsapiapp.Exception.ServiceCustomException.CustomNotFoundException;
import com.dobudobu.newsapiapp.Repository.ArticleReportRepository;
import com.dobudobu.newsapiapp.Repository.ArticlesRepository;
import com.dobudobu.newsapiapp.Repository.CommentReplyRepository;
import com.dobudobu.newsapiapp.Repository.ReportTypeRepository;
import com.dobudobu.newsapiapp.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private ArticleReportRepository articleReportRepository;

    @Override
    public ResponseHandling createReportType(ReportTypeRequest reportTypeRequest) {
        //create report type used for create type of report

        ResponseHandling responseHandling = new ResponseHandling();

        ReportType reportType = new ReportType();
        reportType.setReportName(reportTypeRequest.getReportType());
        reportTypeRepository.save(reportType);

        responseHandling.setMessage("success create report type");
        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setErrors(false);

        return responseHandling;
    }

    @Override
    public ResponseHandling reportArticle(String articlesCode, ReportArticleRequest reportArticleRequest) {
        // this method used for reporting an article

        ResponseHandling responseHandling = new ResponseHandling();

        Optional<Articles> articles = articlesRepository.findByArticlesCode(articlesCode);
        if (!articles.isPresent()){
            throw new CustomNotFoundException("articles code not found");
        }

        //search list of report type by id
        List<ReportType> reportType = reportTypeRepository.findAllById(reportArticleRequest.getReportId());

        if (reportType.isEmpty()){
            throw new CustomNotFoundException("report type not found");
        }

        ArticleReport articleReport = new ArticleReport();
        articleReport.setNote(reportArticleRequest.getNote());
        articleReport.setArticles(articles.get());
        articleReport.setReportTypes(reportType);
        articleReportRepository.save(articleReport);

        responseHandling.setMessage("successfully report article");
        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setErrors(false);

        return responseHandling;
    }

    @Override
    public ResponseHandling<List<ReportTypeGetResponse>> getReportType() {
        ResponseHandling<List<ReportTypeGetResponse>> responseHandling = new ResponseHandling<>();
        List<ReportType> reportTypes = reportTypeRepository.findAll();
        if (reportTypes.isEmpty()){
            throw new CustomNotFoundException("report type not found");
        }

        List<ReportTypeGetResponse> reportTypeGetResponses = reportTypes.stream().map((x)-> {
            ReportTypeGetResponse reportTypeGetResponse = new ReportTypeGetResponse();
            reportTypeGetResponse.setId(x.getId());
            reportTypeGetResponse.setReportName(x.getReportName());
            return reportTypeGetResponse;
        }).collect(Collectors.toList());

        responseHandling.setData(reportTypeGetResponses);
        responseHandling.setMessage("success get report type");
        responseHandling.setHttpStatus(HttpStatus.OK);
        responseHandling.setErrors(false);

        return responseHandling;
    }

}
