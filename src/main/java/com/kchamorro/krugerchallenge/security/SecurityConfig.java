package com.kchamorro.krugerchallenge.security;

import com.kchamorro.krugerchallenge.exception.CustomAccessDeniedHandler;
import com.kchamorro.krugerchallenge.exception.CustomAuthenticationFailureHandler;
import com.kchamorro.krugerchallenge.util.enumerator.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.anonymous().disable();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.addFilter(customAuthenticationFilter);

        http.authorizeRequests().antMatchers().denyAll();
        http.authorizeRequests().antMatchers("/api/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/employees/information").hasAnyAuthority(RoleEnum.ROLE_EMPLOYEE.toString(),RoleEnum.ROLE_ADMINISTRATOR.toString());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/employees").hasAnyAuthority(RoleEnum.ROLE_ADMINISTRATOR.toString());
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/employees").hasAnyAuthority(RoleEnum.ROLE_ADMINISTRATOR.toString());
        http.authorizeRequests().anyRequest().authenticated();

        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
        http.formLogin().failureHandler(new CustomAuthenticationFailureHandler());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
