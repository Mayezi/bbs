package com.mayezi.common;

import com.mayezi.model.security.core.MyFilterSecurityInterceptor;
import com.mayezi.model.security.core.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Mayezi
 * Date: 2017-04-11
 * Time: 17:20
 * All rights reserved.
 */

/**
 * 配置权限
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers(
                        "/admin/**",
                        "/topic/add",
                        "/topic/*/delete",
                        "/topic/*/edit",
                        "/Reply/save",
                        "/Reply/*/delete",
                        "/Reply/*/edit",
                        "/Reply/*/up",
                        "/Reply/*/cancelUp",
                        "/notification/**",
                        "/user/set",
                        "/user/changePassword",
                        "/user/refreshToken")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.csrf().ignoringAntMatchers("/api/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

}