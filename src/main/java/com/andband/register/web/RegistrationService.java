package com.andband.register.web;

import com.andband.register.client.accounts.Account;
import com.andband.register.client.accounts.AccountsService;
import com.andband.register.client.auth.AuthService;
import com.andband.register.client.notification.NotificationService;
import com.andband.register.client.profiles.ProfilesService;
import com.andband.register.persistence.RegistrationToken;
import com.andband.register.service.TokenService;
import org.springframework.stereotype.Service;

@Service
class RegistrationService {

    private AccountsService accountsService;
    private AuthService authService;
    private ProfilesService profilesService;
    private TokenService tokenService;
    private NotificationService notificationService;

    public RegistrationService(AccountsService accountsService, AuthService authService, ProfilesService profilesService, TokenService tokenService, NotificationService notificationService) {
        this.accountsService = accountsService;
        this.authService = authService;
        this.profilesService = profilesService;
        this.tokenService = tokenService;
        this.notificationService = notificationService;
    }

    void registerNewUser(Request request) {
        Account account = accountsService.createAccount(request.getName(), request.getEmail());
        authService.createUser(request.getEmail(), request.getPassword(), account.getId());

        String tokenString = tokenService.generateToken(account.getId());
        notificationService.userRegistration(account.getEmail(), account.getName(), tokenString);
    }

    void confirmRegistration(String tokenString) {
        RegistrationToken token = tokenService.getRegistrationToken(tokenString);
        tokenService.validateToken(token);

        Account account = accountsService.getAccountFromId(token.getAccountId());
        authService.enableUser(account.getId());
        profilesService.createProfile(account.getId(), account.getName());

        tokenService.deleteToken(token);

        notificationService.confirmRegistration(account.getEmail(), account.getName());
    }

}
