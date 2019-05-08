package com.andband.register.service;

import com.andband.register.persistence.RegistrationToken;
import com.andband.register.persistence.RegistrationTokenRepository;
import com.andband.register.util.TokenUtil;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private TokenUtil tokenUtil;
    private RegistrationTokenRepository tokenRepository;

    public TokenService(TokenUtil tokenUtil, RegistrationTokenRepository tokenRepository) {
        this.tokenUtil = tokenUtil;
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(String accountId) {
        String tokenString = tokenUtil.generateToken();
        RegistrationToken token = new RegistrationToken();
        token.setAccountId(accountId);
        token.setTokenString(tokenString);
        tokenRepository.save(token);
        return tokenString;
    }

    public RegistrationToken getRegistrationToken(String tokenString) {
        return tokenRepository.findByTokenString(tokenString);
    }

    public void deleteToken(RegistrationToken token) {
        tokenRepository.delete(token);
    }

    public boolean tokenIsExpired(RegistrationToken token) {
        return token.getExpiryDate().getTime() > System.currentTimeMillis();
    }

}
