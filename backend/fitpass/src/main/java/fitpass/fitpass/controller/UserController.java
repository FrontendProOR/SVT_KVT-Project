package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.ChangePasswordDTO;
import fitpass.fitpass.model.dto.JwtAuthenticationRequest;
import fitpass.fitpass.model.dto.UserDTO;
import fitpass.fitpass.model.dto.UserTokenState;
import fitpass.fitpass.model.entity.Administrator;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.security.TokenUtils;
import fitpass.fitpass.service.EmailService;
import fitpass.fitpass.service.UserService;
import fitpass.fitpass.service.implementation.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    UserService userService;
    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;
    TokenUtils tokenUtils;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationManager authManager, UserDetailsService userDetailsService, TokenUtils tokenUtils){
        this.userService = userService;
        this.authenticationManager = authManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> loadAll() {
        List<User> users = this.userService.findAll();
        if(users == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        List<UserDTO> userDtos = new ArrayList<>();
        for(User user:users){
            if(!(user instanceof Administrator)){
                UserDTO userDTO = new UserDTO(user);
                userDtos.add(userDTO);
            }
        }
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails user = (UserDetails) auth.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> create(@RequestBody @Validated UserDTO newUser){
        User user = userService.createUser(newUser);
        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping("/profile/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody @Validated UserDTO userDTO, Principal user) {
        User changedUser = userService.updateUser(userDTO, user.getName());
        if (changedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        UserDTO updatedUserDTO = new UserDTO(changedUser);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User user(Principal user) {
        return this.userService.findByEmail(user.getName());
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordChangeRequest, Principal principal) {
        String email = principal.getName();
        boolean result = userService.changePassword(email, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
        if (result) {
            String subject = "Password is changed.";
            String content = "Password is changed <br>Password is changed<br>Password is changed<br>Password is changed";
            emailService.sendEmail(email, subject, content);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong old password or user does not exist");
        }
    }
}
