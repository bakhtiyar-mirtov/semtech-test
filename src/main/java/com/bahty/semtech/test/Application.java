package com.bahty.semtech.test;

import java.io.File;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.bahty.semtech.test.domain.Department;
import com.bahty.semtech.test.domain.DepartmentSummary;
import com.bahty.semtech.test.service.FileProcessingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);
			
			String fileName = ps.getProperty("file");
            if (fileName == null) {
                return;
            }   
			log.info ("Processing file: {} ", fileName);

			FileProcessingService fileProcessingService = ctx.getBean(FileProcessingService.class);
			List<DepartmentSummary> departmentSummary = fileProcessingService.getDepartmentSummary(new File(fileName));
			departmentSummary.forEach(department -> System.out.println(department));
			
			
			Department smallestDepartment = fileProcessingService.getSmallestDepartment(new File(fileName));
			if (smallestDepartment != null) {
				System.out.println("Smallest department: " + smallestDepartment.getName());
			} else {
				System.out.println("No department found");
			}
			// finish execution
            System.exit(0);
		};
    }
}
