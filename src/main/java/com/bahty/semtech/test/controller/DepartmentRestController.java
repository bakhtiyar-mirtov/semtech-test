package com.bahty.semtech.test.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bahty.semtech.test.domain.Department;
import com.bahty.semtech.test.domain.DepartmentSummary;
import com.bahty.semtech.test.service.FileProcessingService;

@RestController
@RequestMapping("api")
public class DepartmentRestController {
    
    @Autowired
    private FileProcessingService fileProcessingService;


    @PostMapping(value="/departmentSummary")
    public @ResponseBody List<DepartmentSummary> getDepartmentSummary (@RequestParam("file") MultipartFile multipart) throws Exception {
        // TODO: implement any security checks


        File file = new File(System.getProperty("java.io.tmpdir")+"/tempFile.csv");
        multipart.transferTo(file);
        
        return fileProcessingService.getDepartmentSummary(file);
    }

    @PostMapping(value="/smallestDepartment")
    public @ResponseBody Department getSmallestDepartment (@RequestParam("file") MultipartFile multipart) throws Exception {
        // TODO: implement any security checks

        File file = new File(System.getProperty("java.io.tmpdir")+"/tempFile.csv");
        multipart.transferTo(file);
        
        return fileProcessingService.getSmallestDepartment(file);
    }

}
