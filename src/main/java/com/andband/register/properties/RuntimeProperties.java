package com.andband.register.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:com/andband/register/properties/runtime-${env}.properties")
public class RuntimeProperties {

    @Value("${google.recaptcha-api.secret-key}")
    private String recaptchaSecret;

    public String getRecaptchaSecret() {
        return recaptchaSecret;
    }

}
