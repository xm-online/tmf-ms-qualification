package com.icthh.xm.tmf.ms.qualification.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.icthh.xm.tmf.ms.qualification.AbstractSpringBootTest;
import com.icthh.xm.tmf.ms.qualification.web.api.model.POSTREQProductOfferingQualification;
import com.icthh.xm.tmf.ms.qualification.web.api.model.PromotionQualification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Request and response JSON must stay as it was with Jackson 2 / openapi-generator 4.
 */
public class JacksonCompatibilityIntTest extends AbstractSpringBootTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    public void absentRequiredListKeepsModelDefault() {
        // with a required-args constructor Jackson 3 set absent lists to null and @NotNull rejected the request
        POSTREQProductOfferingQualification request =
            jsonMapper.readValue("{\"description\":\"x\"}", POSTREQProductOfferingQualification.class);

        assertThat(request.getRelatedParty()).isEmpty();
        assertThat(request.getProductOfferingQualificationItem()).isEmpty();
    }

    @Test
    public void modelPropertiesKeepDeclarationOrder() {
        PromotionQualification model = new PromotionQualification();
        model.setId("1");
        model.setState("qualified");
        model.setDescription("d");

        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(model));

        assertThat(json.propertyNames()).containsSubsequence("id", "href", "description", "state", "relatedParty");
    }
}
