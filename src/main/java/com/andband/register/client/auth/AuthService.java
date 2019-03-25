package com.andband.register.client.auth;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private RestApiTemplate authWebService;

    public AuthService(@Qualifier("authApi") RestApiTemplate authWebService) {
        this.authWebService = authWebService;
    }

    public void createUser(String accountId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("accountId", accountId);
        params.put("password", password);
        authWebService.post("/user/register?accountId={accountId}&password={password}", params, Void.class);
    }

}
