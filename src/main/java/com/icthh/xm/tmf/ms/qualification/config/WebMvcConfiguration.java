package com.icthh.xm.tmf.ms.qualification.config;

import com.icthh.xm.commons.lep.spring.web.LepInterceptor;
import com.icthh.xm.commons.web.spring.TenantInterceptor;
import com.icthh.xm.commons.web.spring.TenantVerifyInterceptor;
import com.icthh.xm.commons.web.spring.XmLoggingInterceptor;
import com.icthh.xm.commons.web.spring.config.XmWebMvcConfigurerAdapter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

@Configuration
public class WebMvcConfiguration extends XmWebMvcConfigurerAdapter {

    private final ApplicationProperties appProps;
    private final TenantVerifyInterceptor tenantVerifyInterceptor;
    private final LepInterceptor lepInterceptor;

    protected WebMvcConfiguration(LepInterceptor lepInterceptor,
                                  TenantInterceptor tenantInterceptor,
                                  XmLoggingInterceptor xmLoggingInterceptor,
                                  ApplicationProperties appProps, TenantVerifyInterceptor tenantVerifyInterceptor) {
        super(tenantInterceptor, xmLoggingInterceptor);
        this.appProps = appProps;
        this.tenantVerifyInterceptor = tenantVerifyInterceptor;
        this.lepInterceptor = lepInterceptor;
    }

    @Override
    protected void xmAddInterceptors(final InterceptorRegistry registry) {
        registerTenantInterceptorWithIgnorePathPattern(registry, tenantVerifyInterceptor);
        registerTenantInterceptorWithIgnorePathPattern(registry, lepInterceptor);
    }

    @Override
    protected void xmConfigurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    protected List<String> getTenantIgnorePathPatterns() {
        return appProps.getTenantIgnoredPathList();
    }
}
