package com.dobudobu.newsapiapp.Controller;

import com.dobudobu.newsapiapp.Dto.Request.ReportTypeRequest;
import com.dobudobu.newsapiapp.Dto.Response.ResponseHandling;
import com.dobudobu.newsapiapp.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }

}
