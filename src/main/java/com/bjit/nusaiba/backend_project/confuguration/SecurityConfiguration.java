package com.bjit.nusaiba.backend_project.confuguration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(value= {"com.bjit.nusaiba.backend_project.service"})
public class SecurityConfiguration {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}
	@Configuration
	@Order(1)
	public static class ServerSide extends WebSecurityConfigurerAdapter{

		@Autowired
		private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
			// TODO Auto-generated method stub
	        http
	        .antMatcher("/erp/**")
	        .authorizeHttpRequests()
	        .antMatchers("/**/*.js", "/**/*.css").permitAll()
	        .antMatchers("/erp","/erp/registration","/erp/register","/erp/login","/erp/error")
	        .permitAll()
	        .antMatchers("/erp/add_student","/erp/edit_student","/erp/save")
	        .hasAnyAuthority("student","admin")
	        .antMatchers("/erp/delete_student")
	        .hasAuthority("admin")
	        .anyRequest()
	        .authenticated()
	        .and()
	        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .and()
	        .formLogin()
	        .loginPage("/erp/login")
	        .defaultSuccessUrl("/erp")
	        .and().logout().invalidateHttpSession(true)
	        .clearAuthentication(true)
	        .logoutRequestMatcher(new AntPathRequestMatcher("/erp/logout"))
	        .logoutSuccessUrl("/erp");
	        
	        http.csrf().disable();
		}

		
		

	}
	
	@Configuration
	@Order(2)
	public static class ClientSide extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private JwtRequestFilter jwtRequestFilter;
		

		@Autowired
		private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.antMatcher("/api/**")
			.authorizeRequests().antMatchers("/authenticate").permitAll().

	        antMatchers("/api/students/**")
//	        .permitAll()
	        .authenticated()
	        .antMatchers(HttpMethod.GET,"/api/students/**")
	        .hasAnyAuthority("student","admin")
	        .antMatchers(HttpMethod.POST,"/api/students/**")
	        .hasAnyAuthority("admin")
	        .antMatchers(HttpMethod.PUT,"/api/students/**")
	        .hasAnyAuthority("admin")
	        .antMatchers(HttpMethod.DELETE,"/api/students/**")
	        .hasAnyAuthority("admin")
	        .anyRequest().authenticated().and()
	        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);   
	      
		
	        
		}
	}	
	

	
	@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
}
