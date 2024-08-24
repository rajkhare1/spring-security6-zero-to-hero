package com.rajkhare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
        http.csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/myAccount","/myBalance","/myCards","/myLoans").authenticated()
                .requestMatchers("/contact","/notices","/error","/register").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
            return new JdbcUserDetailsManager(dataSource);
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Securtiy 6.3
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
