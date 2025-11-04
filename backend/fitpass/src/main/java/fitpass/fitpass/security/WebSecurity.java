package fitpass.fitpass.security;

import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

    @Autowired
    private UserService userService;
}
