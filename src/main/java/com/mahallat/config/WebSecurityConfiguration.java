package com.mahallat.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan("com.mahallat.config")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

//	@Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;
	
    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	public WebSecurityConfiguration() {
		super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.csrf().disable()
		        .exceptionHandling()
		       // .accessDeniedHandler(accessDeniedHandler)
		        .authenticationEntryPoint(restAuthenticationEntryPoint)
		        .and()
		        .authorizeRequests()
		        .antMatchers("/admin/dashboard/stores").hasAuthority("ADMIN")			
				.antMatchers("/api/user/register/**").permitAll()
				.antMatchers("/forgot").permitAll()
				.antMatchers("/api/forgot-password").permitAll()
				.antMatchers("/reset").permitAll()
				.antMatchers("/api/stores").permitAll()
				.antMatchers("/api/store/**").permitAll()
				.antMatchers("/api/product/likes/**").permitAll()
				.antMatchers("/api/product/**").permitAll()
				.antMatchers("/api/categories").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/api/product/rate").hasAuthority("MOBILE").anyRequest().authenticated()
				.antMatchers("/api/comment/add").hasAuthority("MOBILE").anyRequest().authenticated()
				.antMatchers("/admin/**").hasAuthority("ADMIN,STORE").anyRequest().authenticated()
				.and().formLogin()
				.successHandler(mySuccessHandler)
				.failureHandler(myFailureHandler)
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/admin/dashboard")
				.failureUrl("/")
				.usernameParameter("email")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied").and().httpBasic();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {
        auth.inMemoryAuthentication()
          .withUser("email").password(bCryptPasswordEncoder.encode("password")).roles("STORE")
          .and()
          .withUser("email").password(bCryptPasswordEncoder.encode("password")).roles("ADMIN")
          .and()
          .withUser("email").password(bCryptPasswordEncoder.encode("password")).roles("MOBILE");
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
}
