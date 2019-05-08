package com.andband.register.client.notification;

import com.andband.register.util.RestApiTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private RestApiTemplate mockNotificationApi;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testUserRegistration() {
        String email = "user1@email.com";
        String userName = "user1";
        String tokenString = "token1234";

        NotificationRequest request = new NotificationRequest();
        request.setEmail(email);
        request.setToProfileName(userName);
        request.setText(tokenString);

        notificationService.userRegistration(email, userName, tokenString);

        verify(mockNotificationApi).post(eq("/notification/user-registration"), refEq(request), eq(Void.class));
    }

    @Test
    public void testConfirmRegistration() {
        String email = "user1@email.com";
        String userName = "user1";

        NotificationRequest request = new NotificationRequest();
        request.setEmail(email);
        request.setToProfileName(userName);

        notificationService.confirmRegistration(email, userName);

        verify(mockNotificationApi).post(eq("/notification/confirm-registration"), refEq(request), eq(Void.class));
    }

}
