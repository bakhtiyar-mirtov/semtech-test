package com.bahty.semtech.test.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.bahty.semtech.test.domain.Department;
import com.bahty.semtech.test.domain.DepartmentSummary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessingService {

    public List<DepartmentSummary> getDepartmentSummary (File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        Map<String, DepartmentSummary> departments = new HashMap<>();

        getDepartmentStream(file)
            .forEach(department -> {
                DepartmentSummary summary = departments.get(department.getCode());
                if (summary == null) {
                    summary = new DepartmentSummary();
                    summary.setCode(department.getCode());
                    summary.setName(department.getName());
                    summary.setTotalPopulation(department.getPopulation());
                    summary.setLargestCity(department.getCity());
                    summary.setLargestCityPopulation(department.getPopulation());
                    departments.put(department.getCode(), summary);
                } else {
                    summary.setTotalPopulation(summary.getTotalPopulation() + department.getPopulation());
                    if (department.getPopulation() > summary.getLargestCityPopulation()) {
                        summary.setLargestCity(department.getCity());
                        summary.setLargestCityPopulation(department.getPopulation());
                    }
                }
            });
        
        return departments.values().stream().collect(Collectors.toList());
    }

    public Department getSmallestDepartment (File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            return null;
        }

        Optional<Department> smallestDepartment = getDepartmentStream(file)
            .min(Comparator.comparingLong(Department::getPopulation));
        

        return smallestDepartment.orElse(null);
    }

    protected Stream<Department> getDepartmentStream (File file) {
        try {
            return Files.lines(file.toPath())
                        .skip(1)
                        .map(FileProcessingService::processLine);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    protected static Department processLine (String line) {
        
        try {
            String[] parts = line.split(";", 4);
            Department department = new Department();

            department.setCode(parts[0]);
            department.setCity(parts[1]);   
            department.setPopulation(Long.parseLong(parts[2]));
            department.setName(parts[3]);

            return department;
        } catch (Exception e) {
            log.error("Error processing line ", e);
            return null;
        }
    }

}
