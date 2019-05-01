package com.andband.register.client.notification;

import com.andband.register.util.RestApiTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private RestApiTemplate notificationApi;

    public NotificationService(@Qualifier("notificationApi") RestApiTemplate notificationApi) {
        this.notificationApi = notificationApi;
    }

    public void userRegistration(String email, String userName, String tokenString) {
        NotificationRequest request = new NotificationRequest();
        request.setEmail(email);
        request.setUserTo(userName);
        request.setText(tokenString);
        notificationApi.post("/notification/user-registration", request, Void.class);
    }

}