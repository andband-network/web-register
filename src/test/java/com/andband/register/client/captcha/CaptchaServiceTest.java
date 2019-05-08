package com.andband.register.client.captcha;

import com.andband.register.exception.ApplicationException;
import com.andband.register.properties.RuntimeProperties;
import com.andband.register.util.RestApiTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CaptchaServiceTest {

    @InjectMocks
    private CaptchaService captchaService;

    @Mock
    private RestApiTemplate mockRecaptchaApi;

    @Mock
    private RuntimeProperties mockRuntimeProperties;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testValidateCaptcha() {
        String captchaToken = "captchaToken";
        String recaptchaSecret = "captchaSecret";

        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setSuccess(true);

        Map<String, String> params = new HashMap<>();
        params.put("secret", recaptchaSecret);
        params.put("response", captchaToken);

        when(mockRuntimeProperties.getRecaptchaSecret()).thenReturn(recaptchaSecret);
        when(mockRecaptchaApi.post("/siteverify?secret={secret}&response={response}", params, CaptchaResponse.class)).thenReturn(captchaResponse);

        captchaService.validateCaptcha(captchaToken);

        verify(mockRuntimeProperties).getRecaptchaSecret();
        verify(mockRecaptchaApi).post("/siteverify?secret={secret}&response={response}", params, CaptchaResponse.class);
    }

    @Test(expectedExceptions = ApplicationException.class)
    public void testValidateCaptchaInvalid() {
        String captchaToken = "captchaToken";
        String recaptchaSecret = "captchaSecret";

        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setSuccess(false);

        Map<String, String> params = new HashMap<>();
        params.put("secret", recaptchaSecret);
        params.put("response", captchaToken);

        when(mockRuntimeProperties.getRecaptchaSecret()).thenReturn(recaptchaSecret);
        when(mockRecaptchaApi.post("/siteverify?secret={secret}&response={response}", params, CaptchaResponse.class)).thenReturn(captchaResponse);

        captchaService.validateCaptcha(captchaToken);
    }

}
