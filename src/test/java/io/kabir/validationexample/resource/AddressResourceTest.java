package io.kabir.validationexample.resource;

import io.kabir.validationexample.ValidationExampleApplication;
import io.kabir.validationexample.common.ExceptionTranslator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {ValidationExampleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressResourceTest {

    @Autowired
    AddressResource addressResource;
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(addressResource)
                .setControllerAdvice(new ExceptionTranslator())
                .build();
    }

    @Test
    void verify_addAddress_OK() throws Exception {
        String addressJson = "{\n" +
                "  \"city\": \"Sydney\",\n" +
                "  \"state\": \"NSW\"\n" +
                "}";
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addressJson))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void verify_addAddress_BadRequest_City_Missing() throws Exception {
        String addressWithCityEmpty = "{\n" +
                "  \"city\": \"\",\n" +
                "  \"state\": \"NSW\"\n" +
                "}";
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addressWithCityEmpty))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].detail").value("City is mandatory."));
    }

    @Test
    void verify_addAddress_BadRequest_Body_Missing() throws Exception {

        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.errors[0].detail").value(Matchers.containsStringIgnoringCase("body is missing")));
    }

}