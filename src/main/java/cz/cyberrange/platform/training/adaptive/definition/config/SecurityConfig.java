package cz.cyberrange.platform.training.adaptive.definition.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Whitelist the terminal-access endpoint so it can be accessed without a JWT.
 * Order(1) ensures this chain is evaluated before ResourceServerSecurityConfig.
 */
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers(matchers -> matchers
                .antMatchers("/training-runs/*/terminal-access"))
            .authorizeRequests(auth -> auth
                .anyRequest().permitAll())
            .csrf().disable();
    }
}
