package com.andband.register;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableEurekaClient
@EnableOAuth2Client
@SpringBootApplication
public class RegisterApplication {

    @Value("${andband.accounts-api.endpoint}")
    private String accountApiEndpoint;

    @Value("${andband.auth-api.endpoint}")
    private String authApiEndpoint;

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }

    @Bean("testingApi")
    public RestApiTemplate testingWebService(@Qualifier("oAuth2RestTemplate") OAuth2RestTemplate restTemplate) {
        return createRestApiTemplate(restTemplate, authApiEndpoint);
    }

    @Bean("accountsApi")
    public RestApiTemplate apiWebService(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate) {
        return createRestApiTemplate(restTemplate, accountApiEndpoint);
    }

    @Bean("authApi")
    public RestApiTemplate authWebService(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate) {
        return createRestApiTemplate(restTemplate, authApiEndpoint);
    }

    private RestApiTemplate createRestApiTemplate(RestTemplate restTemplate, String apiEndpoint) {
        RestApiTemplate restApiTemplate = new RestApiTemplate(restTemplate, apiEndpoint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restApiTemplate.setHttpHeaders(headers);
        return restApiTemplate;
    }

}
