package com.dobudobu.newsapiapp.Service.Impl;

import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Repository.CommentReplyRepository;
import com.dobudobu.newsapiapp.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Override
    public ResponseHandling createReportType(ReportTypeRequest reportTypeRequest) {
        return null;
    }
}
