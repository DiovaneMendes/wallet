package br.com.dbc.wallet.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable().authorizeRequests()
            .antMatchers( HttpMethod.POST, "/api/usuario/registrar").permitAll()
            .antMatchers( HttpMethod.POST, "/api/servico/cancelar/{id}").permitAll()
            .anyRequest().authenticated()
            .and().cors()
            .and().addFilterAfter(
                    new JWTLoginFilter("/api/usuario/login", authenticationManager() ),
                    UsernamePasswordAuthenticationFilter.class )
            .addFilterAfter(
                    new JWTAuthenticatorFilter(),
                    UsernamePasswordAuthenticationFilter.class );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService( userDetailsService )
                .passwordEncoder( encode() );
    }

    @Bean
    public PasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }
}
