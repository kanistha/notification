package org.notification.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import java.util.*

@Configuration
@EnableWebSecurity

class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Bean
    fun users(): UserDetailsService {
        return InMemoryUserDetailsManager(
                Collections.singleton(User
                        .withUsername("foo")
                        .password("pwd")
                        .roles("ADMIN")
                        .build()
                ))
    }

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/").hasRole("USER")
                .anyRequest().authenticated()

    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {

        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("ADMIN")

                .and()
                .withUser("a")
                .password("a")
                .roles("USER")
    }

//    @Bean
//    fun httpSessionStrategy(): HttpSessionStrategy {
//        //return CookieHttpSessionStrategy()
//        return HeaderHttpSessionStrategy()
//    }


}