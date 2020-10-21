package com.icthh.xm.tmf.ms.qualification.web.rest;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.qualification.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.qualification.web.api.ProductOfferingQualificationApiDelegate;
import com.icthh.xm.tmf.ms.qualification.web.api.model.ProductOfferingQualification;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@LepService(group = "service")
public class ProductOfferingQualificationDelegate implements ProductOfferingQualificationApiDelegate {

    @Timed
    @Override
    @LogicExtensionPoint(value = "ProductOfferingQualificationGet", resolver = ProfileKeyResolver.class)
    @PrivilegeDescription("Privilege to get a product offering qualification")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.get('profile')}, 'QUALIFICATION.PRODUCT.OFFERING.GET')")
    public ResponseEntity<ProductOfferingQualification> productOfferingQualificationGet(String productOfferingQualificationId) {
        return ResponseEntity.ok(new ProductOfferingQualification());
    }
}
