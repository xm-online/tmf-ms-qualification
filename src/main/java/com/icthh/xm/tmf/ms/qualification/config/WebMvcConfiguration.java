package com.icthh.xm.tmf.ms.qualification.config;

import com.icthh.xm.commons.web.spring.TenantVerifyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Tenant, logging and LEP interceptors are registered by xm-commons {@code WebMvcConfig}; registering them
 * here again made every request destroy the tenant context twice. xm-commons picks up only
 * {@code AsyncHandlerInterceptor}s, so the suspended-tenant check is still registered here, after the
 * tenant context is set.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final ApplicationProperties appProps;
    private final TenantVerifyInterceptor tenantVerifyInterceptor;

    public WebMvcConfiguration(ApplicationProperties appProps, TenantVerifyInterceptor tenantVerifyInterceptor) {
        this.appProps = appProps;
        this.tenantVerifyInterceptor = tenantVerifyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantVerifyInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(appProps.getTenantIgnoredPathList())
            .order(Ordered.LOWEST_PRECEDENCE);
    }
}
