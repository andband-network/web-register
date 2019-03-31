package com.andband.register.client.profiles;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfilesService {

    private RestApiTemplate profilesApi;

    public ProfilesService(@Qualifier("profilesApi") RestApiTemplate profilesApi) {
        this.profilesApi = profilesApi;
    }

    public void createProfile(String accountId, String name) {
        Profile profile = new Profile();
        profile.setAccountId(accountId);
        profile.setName(name);
        profilesApi.post("/profiles", profile, Profile.class);
    }

}
