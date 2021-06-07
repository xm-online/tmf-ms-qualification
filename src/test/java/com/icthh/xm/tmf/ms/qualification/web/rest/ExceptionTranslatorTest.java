package com.icthh.xm.tmf.ms.qualification.web.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.commons.exceptions.BusinessException;
import com.icthh.xm.commons.i18n.error.web.ExceptionTranslator;
import com.icthh.xm.commons.i18n.spring.service.LocalizationMessageService;
import com.icthh.xm.tmf.ms.qualification.QualificationApp;
import com.icthh.xm.tmf.ms.qualification.config.SecurityBeanOverrideConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class,
    QualificationApp.class, ExceptionTranslatorTest.TestController.class})
public class ExceptionTranslatorTest {
    public static final String EXPECTED_CODE = "expected.code";
    public static final String EXPECTED_MESSAGE = "expected.message";

    @MockBean
    LocalizationMessageService localizationMessageService;
    @Autowired
    private TestController controller;
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    void shouldReturnBadRequestWhenBusinessExceptionThrown() throws Exception {
        when(localizationMessageService.getMessage(any(), any(), any(Boolean.class), any())).thenReturn(EXPECTED_MESSAGE);

        mockMvc.perform(get("/test"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(EXPECTED_CODE))
            .andExpect(jsonPath("$.error_description").value(EXPECTED_MESSAGE));
    }

    @RestController
    @RequestMapping("/test")
    static class TestController {

        @GetMapping
        public void get() {
            throw new BusinessException(EXPECTED_CODE, EXPECTED_MESSAGE);
        }
    }
}
