package tacos;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		//{noop} encoder related with spring default password encoder
		//Prior spring 5.0 default was PasswordEncoder, later on, now stands for DelegatingPasswordEncoder
		
		/** In memory authetication example
		 
			auth.inMemoryAuthentication()
					.withUser("buzz").password("{noop}secret").authorities("ADMIN")
					.and()
					.withUser("woody").password("{noop}secret").authorities("ROLE_USER");
		 **/
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
	        .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/h2-console/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin();
		
		http.csrf().ignoringAntMatchers("/h2-console/**");
    	http.headers().frameOptions().sameOrigin();
	}

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	

}
