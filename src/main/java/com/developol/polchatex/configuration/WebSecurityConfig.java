package com.developol.polchatex.configuration;

import com.developol.polchatex.persistence.User;
import com.developol.polchatex.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserRepository userRepository;

    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic().and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/security/tknauth").hasRole("USER")
                .antMatchers(HttpMethod.OPTIONS, "/rest/**").permitAll()
                .antMatchers(HttpMethod.GET, "/rest/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/rest/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/rest/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/rest/**").hasRole("USER")
                //.antMatchers("/socket/**").hasRole("USER")
                .and()
                .csrf()
                .disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }
    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://polchatex-front.herokuapp.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }


    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        Iterable<User> databaseUserList = userRepository.findAll();
        LinkedList<UserDetails> securityUserList = new LinkedList<>();
        Iterator i = databaseUserList.iterator();
        User u;
        while (i.hasNext()) {
            u = (User) i.next();
            securityUserList.add(
                    org.springframework.security.core.userdetails.User.builder()
                            .username(u.getUsername())
                            .password(u.getPassword())
                            .roles("USER")
                            .build()
            );
        }
        return new InMemoryUserDetailsManager(securityUserList);
    }

}

