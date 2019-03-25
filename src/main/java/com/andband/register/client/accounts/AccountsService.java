package com.andband.register.client.accounts;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    private RestApiTemplate accountsApi;

    public AccountsService(@Qualifier("accountsApi") RestApiTemplate accountsApi) {
        this.accountsApi = accountsApi;
    }

    public Account createAccount(String email) {
        Account account = new Account();
        account.setEmail(email);
        return accountsApi.post(account, Account.class);
    }

}
