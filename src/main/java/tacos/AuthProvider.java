package tacos;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import tacos.services.UserServiceImpl;

@Component
public class AuthProvider implements AuthenticationProvider {
	
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
			
		
		UserDetails user = userService.loadUserByUsername(auth.getName());
		
		if (user == null) {
			throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
		}
		
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
