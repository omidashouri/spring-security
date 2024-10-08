package ir.omidashouri.springsecurity.controllers;

import ir.omidashouri.springsecurity.entities.UserEntity;
import ir.omidashouri.springsecurity.pojo.request.AuthenticationRequest;
import ir.omidashouri.springsecurity.pojo.response.AuthenticationResponse;
import ir.omidashouri.springsecurity.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/public")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;
    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/token")
    public String getToken(@RequestBody UserEntity user) {
        Authentication authentication =
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest request) {
        log.info("createToken(-)");
        // Authenticate the user
        userDetailsService.loadUserByUsername(request.getUsername());

        // Generate the token
        String jwtToken = jwtService.generateToken(request.getUsername());

        return new AuthenticationResponse(jwtToken);
    }
}
