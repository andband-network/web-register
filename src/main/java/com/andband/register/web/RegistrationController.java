package com.andband.register.web;

import com.andband.register.client.captcha.CaptchaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private RegistrationService registrationService;
    private CaptchaService captchaService;

    public RegistrationController(RegistrationService registrationService, CaptchaService captchaService) {
        this.registrationService = registrationService;
        this.captchaService = captchaService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@Valid @RequestBody Request request) {
        captchaService.validateCaptcha(request.getCaptchaToken());
        registrationService.registerNewUser(request);
    }

    @PostMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmRegistration(@PathVariable("token") String token) {
        registrationService.confirmRegistration(token);
    }

}
