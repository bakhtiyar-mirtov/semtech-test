package com.bahty.semtech.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentRestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    
    @Test
    public void testRestAPI() throws Exception {
        mockMvc.perform(post("/api/departmentSummery")
                        .header("content-type", MediaType.APPLICATION_JSON));

        // TODO: complete tests

    }

}

