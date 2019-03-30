package com.andband.register.web;

import com.andband.register.client.accounts.Account;
import com.andband.register.client.accounts.AccountsService;
import com.andband.register.client.auth.AuthService;
import org.springframework.stereotype.Service;

@Service
class RegistrationService {

    private AccountsService accountsService;
    private AuthService authService;

    public RegistrationService(AccountsService accountsService, AuthService authService) {
        this.accountsService = accountsService;
        this.authService = authService;
    }

    void registerNewUser(Request request) {
        Account account = accountsService.createAccount(request.getEmail());
        authService.createUser(request.getEmail(), request.getPassword(), account.getId());
    }

}
