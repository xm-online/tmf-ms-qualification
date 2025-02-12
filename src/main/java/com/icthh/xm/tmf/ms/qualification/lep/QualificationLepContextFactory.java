package com.icthh.xm.tmf.ms.qualification.lep;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.lep.api.LepContextFactory;
import com.icthh.xm.commons.lep.commons.CommonsExecutor;
import com.icthh.xm.commons.lep.commons.CommonsService;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.lep.api.LepMethod;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QualificationLepContextFactory implements LepContextFactory {

    private final TenantConfigService tenantConfigService;
    private final RestTemplate restTemplate;
    private final PermissionCheckService permissionCheckService;
    private final MeterRegistry meterRegistry;

    public QualificationLepContextFactory(TenantConfigService tenantConfigService,
                                          @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
                                          PermissionCheckService permissionCheckService,
                                          MeterRegistry meterRegistry) {
        this.tenantConfigService = tenantConfigService;
        this.restTemplate = restTemplate;
        this.permissionCheckService = permissionCheckService;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public BaseLepContext buildLepContext(LepMethod lepMethod) {
        LepContext lepContext = new LepContext();
        lepContext.services = new LepContext.LepServices();
        lepContext.services.tenantConfigService = tenantConfigService;
        lepContext.services.permissionCheckService = permissionCheckService;
        lepContext.templates = new LepContext.LepTemplates();
        lepContext.templates.rest = restTemplate;
        lepContext.meterRegistry = meterRegistry;
        return lepContext;
    }
}
