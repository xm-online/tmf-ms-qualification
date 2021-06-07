package com.icthh.xm.tmf.ms.qualification.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.tmf.ms.qualification.web.api.ProductOfferingQualificationApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = ProductOfferingQualificationApiController.class, secure = false)
@ContextConfiguration(classes = {ProductOfferingQualificationApiController.class,
    ProductOfferingQualificationDelegate.class})
class ProductOfferingQualificationDelegateTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductOfferingQualificationDelegate delegate;

    @Test
    void shouldReturnNotImplemented() throws Exception {
        mockMvc.perform(get("/api/productOfferingQualificationManagement/v1/productOfferingQualification/123"))
            .andExpect(status().isNotImplemented());
    }
}
