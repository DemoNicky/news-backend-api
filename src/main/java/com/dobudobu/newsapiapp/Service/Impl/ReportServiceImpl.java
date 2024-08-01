package com.dobudobu.newsapiapp.Service.Impl;

import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Entity.Articles;
import com.dobudobu.newsapiapp.Entity.ReportType;
import com.dobudobu.newsapiapp.Exception.ServiceCustomException.CustomNotFoundException;
import com.dobudobu.newsapiapp.Repository.ArticlesRepository;
import com.dobudobu.newsapiapp.Repository.CommentReplyRepository;
import com.dobudobu.newsapiapp.Repository.ReportTypeRepository;
import com.dobudobu.newsapiapp.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

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
    public ResponseHandling reportArticle(String articlesCode, Long reportId) {
        // this method used for reporting an article

        ResponseHandling responseHandling = new ResponseHandling();

        Optional<Articles> articles = articlesRepository.findByArticlesCode(articlesCode);
        if (!articles.isPresent()){
            throw new CustomNotFoundException("articles code not found");
        }

        Optional<ReportType> reportType = reportTypeRepository.findById(reportId);
        if (!reportType.isPresent()){
            throw new CustomNotFoundException("articles code not found");
        }


        return null;
    }


}
