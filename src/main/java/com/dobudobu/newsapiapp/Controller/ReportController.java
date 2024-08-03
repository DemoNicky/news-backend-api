package com.dobudobu.newsapiapp.Controller;

import com.dobudobu.newsapiapp.Dto.Request.ReportArticleRequest;
import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ReportArticleGetResponse;
import com.dobudobu.newsapiapp.Dto.Response.ReportTypeGetResponse;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(
            path = "/create-report-type",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling>createReportType(@RequestBody ReportTypeRequest reportTypeRequest){
        ResponseHandling responseHandling = reportService.createReportType(reportTypeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PostMapping(
            path = "/report-article/{articlesCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling>reportArticle(@PathVariable("articlesCode") String articlesCode,
                                                         @RequestBody ReportArticleRequest reportArticleRequest){
        ResponseHandling responseHandling = reportService.reportArticle(articlesCode, reportArticleRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }


    @GetMapping(
            path = "/get-report-type",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling<List<ReportTypeGetResponse>>>getReportType(){
        ResponseHandling<List<ReportTypeGetResponse>> responseHandling = reportService.getReportType();
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(
            path = "/get-report-article/{page}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseHandling<List<ReportArticleGetResponse>>>getResponseArticle(@PathVariable("page") int page){
        ResponseHandling<List<ReportArticleGetResponse>> responseHandling = reportService.getResponseArticle(page);
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

}
