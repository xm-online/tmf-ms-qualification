package com.icthh.xm.tmf.ms.qualification.config;

import static java.util.Optional.ofNullable;

import com.icthh.xm.commons.permission.constants.RoleConstant;
import com.icthh.xm.commons.security.oauth2.ConfigSignatureVerifierClient;
import com.icthh.xm.commons.security.oauth2.OAuth2JwtAccessTokenConverter;
import com.icthh.xm.commons.security.oauth2.OAuth2Properties;
import com.icthh.xm.commons.security.oauth2.OAuth2SignatureVerifierClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {
    private final OAuth2Properties oAuth2Properties;
    private final ApplicationProperties applicationProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus/**").permitAll()
            .antMatchers("/management/**").hasAuthority(RoleConstant.SUPER_ADMIN);
    }

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(OAuth2SignatureVerifierClient signatureVerifierClient) {
        return new OAuth2JwtAccessTokenConverter(oAuth2Properties, signatureVerifierClient);
    }

    @Bean
	@Qualifier("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        ofNullable(applicationProperties.getLoadBalancedRestTemplate()).ifPresent(properties -> {
            ofNullable(properties.getConnectionRequestTimeout()).ifPresent(requestFactory::setConnectionRequestTimeout);
            ofNullable(properties.getConnectTimeout()).ifPresent(requestFactory::setConnectTimeout);
            ofNullable(properties.getReadTimeout()).ifPresent(requestFactory::setReadTimeout);
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

    @Bean
    public ConfigSignatureVerifierClient configSignatureVerifierClient(
        @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate) {
        return new ConfigSignatureVerifierClient(oAuth2Properties, restTemplate);
    }
}
