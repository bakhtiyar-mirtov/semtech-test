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
                DepartmentSummary summary = departments.get(department.code());
                if (summary == null) {
                    summary = new DepartmentSummary();
                    summary.setCode(department.code());
                    summary.setName(department.name());
                    summary.setTotalPopulation(department.population());
                    summary.setLargestCity(department.city());
                    summary.setLargestCityPopulation(department.population());
                    departments.put(department.code(), summary);
                } else {
                    summary.setTotalPopulation(summary.getTotalPopulation() + department.population());
                    if (department.population() > summary.getLargestCityPopulation()) {
                        summary.setLargestCity(department.city());
                        summary.setLargestCityPopulation(department.population());
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
            .min(Comparator.comparingLong(Department::population));

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
            return new Department(parts[0], parts[1], Long.parseLong(parts[2]), parts[3]);
        } catch (Exception e) {
            log.error("Error processing line ", e);
            return null;
        }
    }

}
