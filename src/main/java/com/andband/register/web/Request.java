package com.andband.register.web;

import com.andband.register.web.validation.Email;
import com.andband.register.web.validation.Password;

import javax.validation.constraints.NotBlank;

public class Request {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Password
    private String password;

    @NotBlank
    private String captchaToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }

}
