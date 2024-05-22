package com.bahty.semtech.test.domain;

import lombok.Data;

@Data
public class DepartmentSummary {
    String code;
    String largestCity;
    long largestCityPopulation;
    long totalPopulation;
    String name;
}
