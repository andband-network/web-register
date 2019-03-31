package com.andband.register.web;

import com.andband.register.client.accounts.Account;
import com.andband.register.client.accounts.AccountsService;
import com.andband.register.client.auth.AuthService;
import com.andband.register.client.profiles.ProfilesService;
import org.springframework.stereotype.Service;

@Service
class RegistrationService {

    private AccountsService accountsService;
    private AuthService authService;
    private ProfilesService profilesService;

    public RegistrationService(AccountsService accountsService, AuthService authService, ProfilesService profilesService) {
        this.accountsService = accountsService;
        this.authService = authService;
        this.profilesService = profilesService;
    }

    void registerNewUser(Request request) {
        Account account = accountsService.createAccount(request.getName(), request.getEmail());
        authService.createUser(request.getEmail(), request.getPassword(), account.getId());
        profilesService.createProfile(account.getId(), account.getName());
    }

}
