package com.andband.register.client.auth;

import com.andband.register.util.RestApiTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private RestApiTemplate mockAuthApi;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testCreateUser() {
        String email = "user1@email.com";
        String password = "password1";
        String accountId = "account123";

        authService.createUser(email, password, accountId);

        verify(mockAuthApi).post(eq("/user/register?username={email}&password={password}&accountId={accountId}"), any(HashMap.class), eq(Void.class));
    }

    @Test
    public void testEnableUser() {
        String accountId = "account123";

        authService.enableUser(accountId);

        verify(mockAuthApi).post(eq("/user/enable?accountId={accountId}"), any(HashMap.class), eq(Void.class));
    }

    @Test
    public void testDeleteUser() {
        String accountId = "account123";

        authService.deleteUser(accountId);

        verify(mockAuthApi).delete(eq("/user?accountId={accountId}"), any(HashMap.class));
    }

}
