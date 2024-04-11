package com.example.library.management.Controllers;

import com.example.library.management.Services.PatronService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPatrons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());



    }

    @Test
    void addPatron() throws Exception {
        String requestBody = "{\"name\":\"John Doe\",\"address\":\"123 Main Street\",\"phoneNumber\":\"+123456789012\",\"email\":\"johndoe@example.com\"}";
            mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(MockMvcResultMatchers.status().isCreated());


    }
    @Test
    void addPatron1() throws Exception {
        // Invalid patron with invalid phone number and email
        String requestBody = "{\"name\":\"John Doe\",\"address\":\"123 Main Street\",\"phoneNumber\":\"+12345678\",\"email\":\"johndoe\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    void getPatronById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Mark john"))
                .andExpect(jsonPath("$.address").value("123 Main St, Anytown"))
                .andExpect(jsonPath("$.phoneNumber").value("+123456789067"))
                .andExpect(jsonPath("$.email").value("mark_john@example.com"));
    }

    @Test
    void updatePatron() throws Exception {
        String requestBody = "{\"name\":\"John Doe\",\"address\":\"123 Main Street\",\"phoneNumber\":\"+201029979868\",\"email\":\"johndoe@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void updatePatron2() throws Exception {
        String requestBody = "{\"name\":\"\",\"address\":\"123 Main Street\",\"phoneNumber\":\"+201029979868\",\"email\":\"johndoe@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deletePatron() {
        PatronService patronService = mock(PatronService.class);
        when(patronService.deletePatron(1L)).thenReturn(true);
        PatronController patronController = new PatronController(patronService);
        assertEquals("Patron deleted successfully", patronController.deletePatron(1L).getBody());
    }
}