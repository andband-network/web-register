package com.andband.register.service;

import com.andband.register.persistence.RegistrationToken;
import com.andband.register.persistence.RegistrationTokenRepository;
import com.andband.register.util.TokenUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenUtil mockTokenUtil;

    @Mock
    private RegistrationTokenRepository mockTokenRepository;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testGenerateToken() {
        String accountId = "account123";

        String expectedTokenString = "token1234";

        RegistrationToken expectedRegistrationToken = new RegistrationToken();
        expectedRegistrationToken.setAccountId(accountId);
        expectedRegistrationToken.setTokenString(expectedTokenString);

        when(mockTokenUtil.generateToken()).thenReturn(expectedTokenString);

        String actualTokenString = tokenService.generateToken(accountId);

        assertThat(actualTokenString).isEqualTo(expectedTokenString);

        verify(mockTokenUtil).generateToken();
        verify(mockTokenRepository).save(refEq(expectedRegistrationToken));
    }

    @Test
    public void testGetRegistrationToken() {
        String tokenString = "token1234";

        RegistrationToken expectedRegistrationToken = new RegistrationToken();
        expectedRegistrationToken.setAccountId("account123");
        expectedRegistrationToken.setTokenString(tokenString);

        when(mockTokenRepository.findByTokenString(tokenString)).thenReturn(expectedRegistrationToken);

        RegistrationToken actualRegistrationToken = tokenService.getRegistrationToken(tokenString);

        assertThat(actualRegistrationToken).isEqualTo(expectedRegistrationToken);

        verify(mockTokenRepository).findByTokenString(tokenString);
    }

    @Test
    public void testDeleteToken() {
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setAccountId("account123");
        registrationToken.setTokenString("token1234");

        tokenService.deleteToken(registrationToken);

        verify(mockTokenRepository).delete(registrationToken);
    }

    @Test
    public void testTokenIsExpiredNotExpired() {
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setExpiryDate(new Date(System.currentTimeMillis() - 999999));
        boolean tokenIsExpired = tokenService.tokenIsExpired(registrationToken);

        assertThat(tokenIsExpired).isFalse();
    }

    @Test
    public void testTokenIsExpiredIsExpired() {
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setExpiryDate(new Date(System.currentTimeMillis() + 99999));
        boolean tokenIsExpired = tokenService.tokenIsExpired(registrationToken);

        assertThat(tokenIsExpired).isTrue();
    }

}
