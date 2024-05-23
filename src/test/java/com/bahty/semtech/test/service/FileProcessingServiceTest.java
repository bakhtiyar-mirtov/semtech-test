package com.bahty.semtech.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bahty.semtech.test.domain.Department;
import com.bahty.semtech.test.domain.DepartmentSummary;

@SpringBootTest
public class FileProcessingServiceTest {

    @Autowired
    private FileProcessingService fileProcessingService;

    @Test
    public void testgetDepartmentSummaryWithNull () {
        List<DepartmentSummary> departments = fileProcessingService.getDepartmentSummary(null);
        assertNotNull(departments);
        assertEquals(0, departments.size());
    }

    @Test 
    public void testgetDepartmentSummaryWithNonExistingFile () throws URISyntaxException {
        List<DepartmentSummary> departments = fileProcessingService.getDepartmentSummary(new File("non-existing-file.csv"));
        assertNotNull(departments);
        assertEquals(0, departments.size());
    }

    @Test 
    public void testgetSmallestDepartmentWitDthEmptyFile () throws URISyntaxException {
        URL emptyFileUrl = getClass().getClassLoader().getResource("emptyFile.csv");
        Department smallestDepartment = fileProcessingService.getSmallestDepartment(new File(emptyFileUrl.toURI()));
        assertNull(smallestDepartment);
    }

    @Test
    public void testgetSmallestDepartmentWithNull () {
        Department smallestDepartment =  fileProcessingService.getSmallestDepartment(null);
        assertNull(smallestDepartment);

    }

    @Test 
    public void testgetSmallestDepartmentWithNonExistingFile () throws URISyntaxException {
        Department smallestDepartment =  fileProcessingService.getSmallestDepartment(new File("non-existing-file.csv"));
        assertNull(smallestDepartment);

    }

    @Test 
    public void testgetDepartmentSummaryWithEmptyFile () throws URISyntaxException {
        URL emptyFileUrl = getClass().getClassLoader().getResource("emptyFile.csv");
        List<DepartmentSummary> departments = fileProcessingService.getDepartmentSummary(new File(emptyFileUrl.toURI()));
        assertNotNull(departments);
        assertEquals(0, departments.size());
    }

    @Test
    public void testStringProcessingOfEmptyString () throws URISyntaxException {
        Department department = fileProcessingService.processLine("");
        assertNull(department);
    }

    @Test
    public void testStringProcessingOfStringWithPartialElements () throws URISyntaxException {
        Department department = fileProcessingService.processLine("08;Landrichamps;135;");
        assertNotNull(department);
        assertEquals("08", department.code());
        assertEquals("Landrichamps", department.city());
        assertEquals(135, department.population());
        assertTrue(department.name().isEmpty());
    }

    @Test
    public void testStringProcessingOfValidString () throws URISyntaxException {
        Department department = fileProcessingService.processLine("21;Aubigny-lès-Sombernon;150;COTE-D'OR");
        assertEquals("21", department.code());
        assertEquals("Aubigny-lès-Sombernon", department.city());
        assertEquals(150, department.population());
        assertEquals("COTE-D'OR", department.name());
    }

    @Test
    public void testgetDepartmentSummaryWithValidFile () throws URISyntaxException {
        URL validFileUrl = getClass().getClassLoader().getResource("validFile.csv");
        List<DepartmentSummary> departments = fileProcessingService.getDepartmentSummary(new File(validFileUrl.toURI()));
        System.out.println(departments.size());
        assertNotNull(departments);
        assertEquals(2, departments.size());
        assertEquals(150, departments.get(0).getTotalPopulation());
    }

    @Test
    public void testSmallestDepartmentWithValidFile () throws URISyntaxException {
        URL validFileUrl = getClass().getClassLoader().getResource("validFile.csv");
        Department smallestDepartment = fileProcessingService.getSmallestDepartment(new File(validFileUrl.toURI()));
        System.out.println(smallestDepartment);
        assertEquals("03", smallestDepartment.code());
    }
}

