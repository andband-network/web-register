package com.andband.register.client.captcha;

import com.andband.register.exception.ApplicationException;
import com.andband.register.properties.RuntimeProperties;
import com.andband.register.util.RestApiTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {

    private RestApiTemplate recaptchaApi;
    private RuntimeProperties runtimeProperties;

    public CaptchaService(RestApiTemplate recaptchaApi, RuntimeProperties runtimeProperties) {
        this.recaptchaApi = recaptchaApi;
        this.runtimeProperties = runtimeProperties;
    }

    public void validateCaptcha(String captchaToken) {
        Map<String, String> params = new HashMap<>();
        params.put("secret", runtimeProperties.getRecaptchaSecret());
        params.put("response", captchaToken);
        CaptchaResponse response = recaptchaApi.post("/siteverify?secret={secret}&response={response}", params, CaptchaResponse.class);
        if (!response.isSuccess()) {
            throw new ApplicationException("invalid captcha token");
        }
    }

}
