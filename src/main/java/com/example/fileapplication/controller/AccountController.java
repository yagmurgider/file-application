package com.example.fileapplication.controller;

import com.example.fileapplication.dto.FileDTO;
import com.example.fileapplication.dto.LoginRequest;
import com.example.fileapplication.dto.RegistrationRequest;
import com.example.fileapplication.dto.TokenResponse;
import com.example.fileapplication.entity.User;
import com.example.fileapplication.repository.UserRepository;
import com.example.fileapplication.security.JwtTokenUtil;
import com.example.fileapplication.service.impl.UserServiceImpl;
import com.example.fileapplication.util.ApiPaths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiPaths.AccountCtrl.CTRL)
@Api(value = ApiPaths.AccountCtrl.CTRL, description = "Account APIs")
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Login", response = TokenResponse.class)
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final User user = userRepository.findByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new TokenResponse(user.getUsername(), token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "Register", response = Boolean.class)
    public ResponseEntity<Boolean> register(@RequestBody RegistrationRequest registrationRequest) throws AuthenticationException {
        Boolean response = userService.register(registrationRequest);
        return ResponseEntity.ok(response);
    }

}
