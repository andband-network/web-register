package com.andband.register.persistence;

import org.springframework.data.repository.CrudRepository;

public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, Long> {

    RegistrationToken findByTokenString(String tokenString);

}
