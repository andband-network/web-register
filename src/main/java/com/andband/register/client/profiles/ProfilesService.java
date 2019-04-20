package com.andband.register.client.profiles;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProfilesService {

    private RestApiTemplate profilesApi;

    public ProfilesService(@Qualifier("profilesApi") RestApiTemplate profilesApi) {
        this.profilesApi = profilesApi;
    }

    public void createProfile(String accountId, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("accountId", accountId);
        params.put("name", name);
        profilesApi.post("/profiles/?accountId={accountId}&name={name}", params, Void.class);
    }

}
