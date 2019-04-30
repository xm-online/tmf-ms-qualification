package com.icthh.xm.tmf.ms.qualification.web.rest;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.tmf.ms.qualification.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.qualification.web.api.PromotionQualificationApiDelegate;
import com.icthh.xm.tmf.ms.qualification.web.api.model.Characteristic;
import com.icthh.xm.tmf.ms.qualification.web.api.model.PromotionQualification;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service", name = "default")
public class PromotionQualificationDelegate implements PromotionQualificationApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "PromotionQualificationFind", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({}, 'QUALIFICATION.PROMOTION.GET')")
    @Override
    public ResponseEntity<PromotionQualification> promotionQualificationFind(String profile,
                                                                             String relatedPartyId,
                                                                             String channelId,
                                                                             Characteristic characteristic) {
        return ResponseEntity.ok(new PromotionQualification());
    }
}
