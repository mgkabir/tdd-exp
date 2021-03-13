package io.kabir.validationexample.resource;

import io.kabir.validationexample.ValidationExampleApplication;
import io.kabir.validationexample.common.ExceptionTranslator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {ValidationExampleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeResourceTest {

    @Autowired
    HomeResource homeResource;
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(homeResource)
                .setControllerAdvice(new ExceptionTranslator())
                .build();
    }

    @Test
    void getDayNumber_InRange_OK() throws Exception{
        mockMvc.perform(get("/day-number")
                .contentType(MediaType.APPLICATION_JSON).param("dayNumber", "5"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getDayNumber_OutOfRange_BadRequest() throws Exception{
        mockMvc.perform(get("/day-number")
                .contentType(MediaType.APPLICATION_JSON).param("dayNumber", "10"))
                //.andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors[0].detail").value("Day number has to be less than or equal to 7"));
    }

    @Test
    void getConsents_OK() throws Exception{
        mockMvc.perform(get("/{tenantId}/grants","Unicorn")
                .contentType(MediaType.APPLICATION_JSON)
                .param("include", "Pending")
                .param("periodStart", "2021-03-15")
                .param("scope", "bank:detail:read"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should throw BadRequest when Mandatory query Parameter is missing")
    void getConsents_BadRequest_Include_Missing() throws Exception{
        mockMvc.perform(get("/{tenantId}/grants","Unicorn")
                .contentType(MediaType.APPLICATION_JSON)
                //.param("include", "Pending")
                .param("periodStart", "2021-03-15")
                .param("scope", "bank:detail:read"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}