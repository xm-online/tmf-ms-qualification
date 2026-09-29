package com.icthh.xm.tmf.ms.qualification.config;

import static java.util.Optional.ofNullable;

import java.time.Duration;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    private final ApplicationProperties applicationProperties;

    public RestTemplateConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    @Qualifier("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
        ApplicationProperties.RestTemplateProperties properties = applicationProperties.getLoadBalancedRestTemplate();

        // Spring 7 dropped HttpComponentsClientHttpRequestFactory#setConnectTimeout: the connect timeout
        // now belongs to the connection config of the underlying Apache HttpClient
        ConnectionConfig.Builder connectionConfig = ConnectionConfig.custom();
        ofNullable(properties).map(ApplicationProperties.RestTemplateProperties::getConnectTimeout)
            .ifPresent(timeout -> connectionConfig.setConnectTimeout(Timeout.ofMilliseconds(timeout)));
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                    .setDefaultConnectionConfig(connectionConfig.build())
                    .build())
                .useSystemProperties()
                .build());

        ofNullable(properties).ifPresent(props -> {
            ofNullable(props.getConnectionRequestTimeout())
                .ifPresent(timeout -> requestFactory.setConnectionRequestTimeout(Duration.ofMillis(timeout)));
            ofNullable(props.getReadTimeout())
                .ifPresent(timeout -> requestFactory.setReadTimeout(Duration.ofMillis(timeout)));
        });

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        customizer.customize(restTemplate);
        return restTemplate;
    }

    @Bean
    @Qualifier("vanillaRestTemplate")
    public RestTemplate vanillaRestTemplate() {
        return new RestTemplate();
    }
}
