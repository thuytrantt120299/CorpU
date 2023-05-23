package com.example.corpu.security.config;
//import com.example.corpu.appuser.AppUserService;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import java.util.Collections;
//
//@Configuration
//@AllArgsConstructor
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    private final AppUserService appUserService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
////        @Bean
////        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////            http.csrf().disable()
////                    .authorizeRequests()
////                    .antMatchers("/api/v*/**").permitAll()
//////                    .antMatchers("/api/v*/application/**").hasRole(AppUserRole.USER.name())
////                    .anyRequest().authenticated()
////                    .and()
////                    .formLogin()
////                    .disable()
////                    .httpBasic();
////            return http.build();
////        }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/api/v*/registration/**").permitAll()
////                .antMatchers("/api/v*/units/**").hasRole("USER")
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .disable()
////                .httpBasic();
////
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/v*/registration/**").permitAll()
////                .antMatchers("/api/v*/login/**").permitAll()
////                .antMatchers("/api/v*/units/**").hasAnyAuthority("ADMIN","USER")
////                .antMatchers("/api/v*/application/**").hasAnyAuthority("ADMIN","USER")
//                .anyRequest().permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .build();
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(appUserService);
//        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//        return authenticationProvider;
//    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return new ProviderManager(Collections.singletonList(authenticationProvider()));
//    }
//
//    @Bean
//    public BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {
//        BasicAuthenticationFilter filter = new BasicAuthenticationFilter(authenticationManager());
//        return filter;
//    }
//
//}

import com.example.corpu.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Collections;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .antMatchers("/api/v*/login/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/application/**").hasAuthority("USER")
                .antMatchers(HttpMethod.PATCH,"/api/v1/sessionalStaff/**").hasAuthority("USER")
                .antMatchers("/api/v1/units/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/v1/application/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/applications/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/sessionalStaffs/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated().and()
                .formLogin()
                .disable()
                .logout()
                .logoutUrl("/api/v1/logout") // Specify the URL for the logout endpoint
                .logoutSuccessHandler(logoutSuccessHandler()); // Specify the logout success handler
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler(); // Return HTTP status 200 upon successful logout
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
        @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

        @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }
}