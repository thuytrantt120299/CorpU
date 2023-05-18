package com.example.corpu;
import com.example.corpu.appuser.AppUser;
import com.example.corpu.appuser.AppUserRole;
import com.example.corpu.appuser.AppUserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.UUID;

@SpringBootTest
@Rollback(false)
@Log4j2
public class Admin {

    private final AppUserService appUserService;

    @Autowired
    public Admin(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Test
    void createPermanentStaffAccount(){
        AppUser appUser = AppUser.builder()
                .id(UUID.randomUUID().toString())
                .appUserRole(AppUserRole.ADMIN)
                .email("admin@gmail.com")
                .password("admin123")
                .enabled(true)
                .firstName("admin")
                .lastName("admin")
                .build();
        log.debug(appUserService.signUpPermanentStaffAccount(appUser));
    }
}
