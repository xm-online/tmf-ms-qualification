package com.icthh.xm.tmf.ms.qualification.web.rest;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.tmf.ms.qualification.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.qualification.web.api.PromotionQualificationApiDelegate;
import com.icthh.xm.tmf.ms.qualification.web.api.model.PromotionQualification;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@LepService(group = "service", name = "default")
public class PromotionQualificationDelegate implements PromotionQualificationApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "PromotionQualificationFind", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': #profile, 'relatedPartyId': #relatedPartyId, 'channelId': #channelId}, 'QUALIFICATION.PROMOTION.GET')")
    @Override
    public ResponseEntity<PromotionQualification> promotionQualificationFind(String profile,
                                                                             String relatedPartyId,
                                                                             String channelId) {
        if (getRequest().isPresent()) {
            log.info("Native request {}", getRequest().get().getNativeRequest());
        }
        return ResponseEntity.ok(new PromotionQualification());
    }
}
