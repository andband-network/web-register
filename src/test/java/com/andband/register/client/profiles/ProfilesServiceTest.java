package com.andband.register.client.profiles;

import com.andband.register.util.RestApiTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProfilesServiceTest {

    @InjectMocks
    private ProfilesService profilesService;

    @Mock
    private RestApiTemplate profilesApi;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testCreateProfile() {
        String accountId = "accountId";
        String name = "User1";

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("accountId", accountId);
        expectedParams.put("name", name);

        profilesService.createProfile(accountId, name);

        verify(profilesApi).post("/profiles/?accountId={accountId}&name={name}", expectedParams, Void.class);
    }

}