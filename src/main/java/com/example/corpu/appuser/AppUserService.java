package com.example.corpu.appuser;

import com.example.corpu.registration.token.ConfirmationToken;
import com.example.corpu.registration.token.ConfirmationTokenService;
import com.example.corpu.security.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){
        Optional<AppUser> userOpt = appUserRepository
                .findByEmail(appUser.getEmail());

        //case email taken
        if (userOpt.isPresent()){
            AppUser userExist = userOpt.get();
            if (userExist.isEnabled()){
                throw new IllegalStateException("Email already taken");
            }
        }

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .id(UUID.randomUUID().toString())
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .build();

        //case email present but is not enable, update new information
        if (userOpt.isPresent()) {
           appUser.setId(userOpt.get().getId());
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        confirmationToken.setAppUser(appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

            // TODO: SEND EMAIL
            return token;
        }

        public String signUpPermanentStaffAccount(AppUser appUser){
            Optional<AppUser> userOpt = appUserRepository
                    .findByEmail(appUser.getEmail());
            //if email not confirmed send confirmation email
            if (userOpt.isPresent()) {
                AppUser userExist = userOpt.get();
                if (userExist.isEnabled()) {
                    return "Email already taken";
                }else {
                    appUser.setId(userExist.getId());
                }
            }
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);
            appUserRepository.save(appUser);
            return "Created app user successful";
        }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public String findUser(LoginRequest loginRequest) {
        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (appUser.getPassword().equals(encodedPassword)) {
            return jwtTokenUtil.generateToken(appUser.getEmail());
        }
        return null; // Return null if authentication fails
    }


    private String generateToken(AppUser appUser) {
        // Set the expiration time for the token (e.g., 1 hour from the current time)
        Date expiration = new Date(System.currentTimeMillis() + 3600000);

        // Generate the token using the user's email as the subject and a secret key
        String token = Jwts.builder()
                .setSubject(appUser.getEmail())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey")
                .compact();

        return token;
    }

}
