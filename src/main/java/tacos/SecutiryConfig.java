package tacos;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthProvider appAuthProvider; 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(appAuthProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests()
				.antMatchers("/design", "/orders")
				.access("hasRole('ROLE_USER')")
				.antMatchers("/", "/**").access("permitAll")
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/design", true)
			.and()
				.logout()
				.logoutSuccessUrl("/login");
	
	
		http.csrf().ignoringAntMatchers("/h2-console/**");
		//http.csrf().disable();
		
		http.headers().frameOptions().sameOrigin();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	

}
