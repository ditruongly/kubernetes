package learning.spring.security.demo.configuration;

import learning.spring.security.demo.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static learning.spring.security.demo.configuration.ApplicationPermission.*;
import static learning.spring.security.demo.configuration.ApplicationRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "css/*", "/js/*").permitAll() // whitelist URLs
                .antMatchers("/api/v1/students/**").hasAnyRole(STUDENT.name(), ADMIN.name())
                //.antMatchers("/api/v1/management/students/**").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/v1/management/students/**")
                .hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.POST, "/api/v1/management/students/**")
                .hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.PUT, "/api/v1/management/students/**")
                .hasAuthority(STUDENT_WRITE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails jason =User.builder()
                .username("jason")
                .password(passwordEncoder.encode("letmein"))
                .roles(STUDENT.name())
                .build();

        UserDetails bruce =User.builder()
                .username("bruce")
                .password(passwordEncoder.encode("letmein123"))
                .roles(ADMIN.name())
                .build();

        UserDetails tom =User.builder()
                .username("tom")
                .password(passwordEncoder.encode("letmein123"))
                .roles(ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(jason, bruce, tom);
    }
}
