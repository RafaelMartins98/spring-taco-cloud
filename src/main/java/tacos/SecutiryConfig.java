package tacos;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		// Without passwordCompare(), it will try to authenticate the user on
		// LDAP server, otherwise with this method, it will only compare passwords
		
		
		// LDAP Authentication port -> 33389
		
		auth.ldapAuthentication()
			.userSearchBase("ou=people")
			.userSearchFilter("(uid={0})")
			.contextSource()
			.root("dc=tacocloud,dc=com")
			.ldif("classpath:users.ldif").and()
			.passwordCompare()
			.passwordAttribute("userPassword");

	}
	

}
