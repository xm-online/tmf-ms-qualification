package com.icthh.xm.tmf.ms.qualification.lep;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.logging.trace.TraceService;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.client.RestTemplate;

public class LepContext extends BaseLepContext implements TraceService.TraceServiceField {

    public LepServices services;
    public LepTemplates templates;
    public MeterRegistry meterRegistry;

    public static class LepServices {
        public TenantConfigService tenantConfigService;
        public PermissionCheckService permissionCheckService;
    }

    public static class LepTemplates{
        public RestTemplate rest;
    }
}
