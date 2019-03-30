package com.andband.register.client.auth;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private RestApiTemplate authApi;

    public AuthService(@Qualifier("authApi") RestApiTemplate authApi) {
        this.authApi = authApi;
    }

    public void createUser(String email, String password, String accountId) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("accountId", accountId);
        authApi.post("/user/register?username={email}&password={password}&accountId={accountId}", params, Void.class);
    }

}
