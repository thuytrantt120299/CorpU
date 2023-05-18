package com.example.corpu.registration.token;

import java.util.Optional;

public interface ConfirmationTokenService {
    public void saveConfirmationToken(ConfirmationToken confirmationToken);
    public Optional<ConfirmationToken> getToken(String token);
    public int setConfirmedAt(String token);
}
