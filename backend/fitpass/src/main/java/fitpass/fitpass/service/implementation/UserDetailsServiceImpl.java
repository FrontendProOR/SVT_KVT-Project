package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("There is no user with email " + email);
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + user.getRole();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail().trim(),
                    user.getPassword().trim(),
                    grantedAuthorities);
        }
    }
}
