package com.andband.register.web;

import com.andband.register.client.accounts.Account;
import com.andband.register.client.accounts.AccountsService;
import com.andband.register.client.auth.AuthService;
import com.andband.register.client.notification.NotificationService;
import com.andband.register.client.profiles.ProfilesService;
import com.andband.register.exception.ApplicationException;
import com.andband.register.persistence.RegistrationToken;
import com.andband.register.service.TokenService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private AccountsService mockAccountsService;

    @Mock
    private AuthService mockAuthService;

    @Mock
    private ProfilesService mockProfilesService;

    @Mock
    private TokenService mockTokenService;

    @Mock
    private NotificationService mockNotificationService;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testRegisterNewUser() {
        String name = "User1";
        String email = "user1@email.com";
        String password = "password1";
        String accountId = "account123";
        String tokenString = "tokenString123";

        Request request = new Request();
        request.setName(name);
        request.setEmail(email);
        request.setPassword(password);

        Account account = new Account();
        account.setId(accountId);
        account.setName(name);
        account.setEmail(email);

        when(mockAccountsService.createAccount(name, email)).thenReturn(account);
        when(mockTokenService.generateToken(accountId)).thenReturn(tokenString);


        registrationService.registerNewUser(request);

        verify(mockAccountsService).createAccount(name, email);
        verify(mockAuthService).createUser(email, password, accountId);
        verify(mockTokenService).generateToken(accountId);
        verify(mockNotificationService).userRegistration(email, name, tokenString);
    }

    @Test
    public void testConfirmRegistration() {
        String tokenString = "token1234";
        String accountId = "account123";
        String name = "user1";
        String email = "user1@email.com";

        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setAccountId(accountId);

        Account account = new Account();
        account.setId(accountId);
        account.setName(name);
        account.setEmail(email);

        when(mockTokenService.getRegistrationToken(tokenString)).thenReturn(registrationToken);
        when(mockTokenService.tokenIsExpired(registrationToken)).thenReturn(false);
        when(mockAccountsService.getAccountFromId(accountId)).thenReturn(account);

        registrationService.confirmRegistration(tokenString);

        verify(mockTokenService).getRegistrationToken(tokenString);
        verify(mockTokenService).tokenIsExpired(registrationToken);
        verify(mockAccountsService).getAccountFromId(accountId);
        verify(mockAuthService).enableUser(accountId);
        verify(mockProfilesService).createProfile(accountId, name);
        verify(mockTokenService).deleteToken(registrationToken);
        verify(mockNotificationService).confirmRegistration(email, name);
    }

    @Test(expectedExceptions = ApplicationException.class)
    public void testConfirmRegistrationInvalidTokenString() {
        String tokenString = "token1234";

        when(mockTokenService.getRegistrationToken(tokenString)).thenReturn(null);

        registrationService.confirmRegistration(tokenString);
    }

    @Test
    public void testConfirmRegistrationExpiredToken() {
        String tokenString = "token1234";
        String accountId = "account123";

        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setAccountId(accountId);

        when(mockTokenService.getRegistrationToken(tokenString)).thenReturn(registrationToken);
        when(mockTokenService.tokenIsExpired(registrationToken)).thenReturn(true);

        try {
            registrationService.confirmRegistration(tokenString);
        } catch (ApplicationException e) {
            assertThat(e.getMessage()).isEqualTo("Verification token has expired");
        }

        verify(mockAccountsService).deleteAccount(accountId);
        verify(mockAuthService).deleteUser(accountId);
    }

}
