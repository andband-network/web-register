package com.andband.register.client.accounts;

import com.andband.register.util.RestApiTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountsServiceTest {

    @InjectMocks
    private AccountsService accountsService;

    @Mock
    private RestApiTemplate mockAccountsApi;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testGetAccountFromId() {
        String accountId = "account123";

        Account expectedAccount = new Account();

        when(mockAccountsApi.get("/accounts/" + accountId, Account.class)).thenReturn(expectedAccount);

        Account actualAccount = accountsService.getAccountFromId(accountId);

        assertThat(actualAccount).isEqualTo(expectedAccount);

        verify(mockAccountsApi).get("/accounts/" + accountId, Account.class);
    }

    @Test
    public void testCreateAccount() {
        String name = "User1";
        String email = "user1@email.com";

        Account expectedAccount = new Account();

        when(mockAccountsApi.post(eq("/accounts"), any(Account.class), eq(Account.class))).thenReturn(expectedAccount);

        Account actualAccount = accountsService.createAccount(name, email);

        assertThat(actualAccount).isEqualTo(expectedAccount);

        verify(mockAccountsApi).post(eq("/accounts"), any(Account.class), eq(Account.class));
    }

    @Test
    public void testDeleteAccount() {
        String accountId = "account123";

        accountsService.deleteAccount(accountId);

        verify(mockAccountsApi).delete("/accounts/" + accountId);
    }

}
