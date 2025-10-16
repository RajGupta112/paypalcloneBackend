package com.paypal.user_service.controller;

import com.paypal.user_service.dto.JwtRequest;
import com.paypal.user_service.dto.JwtResponse;
import com.paypal.user_service.dto.LoginRequest;
import com.paypal.user_service.dto.SignupRequest;
import com.paypal.user_service.entity.User;
import com.paypal.user_service.entity.UserRole;
import com.paypal.user_service.repository.UserRepository;
import com.paypal.user_service.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final UserRepository userRepository;
      private  final PasswordEncoder passwordEncoder;

    private  final JWTUtil jwtUtil;



    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest){


        Optional<User> existingUser= userRepository.findByEmail(signupRequest.getEmail());
        if(existingUser.isPresent()){
            return  ResponseEntity.badRequest().body("User already Exist");
        }

        User user= new User();
         user.setName(signupRequest.getName());
         user.setEmail(signupRequest.getEmail());
         user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
         user.setRole(UserRole.valueOf("USER"));


         User savedUser= userRepository.save(user);
         return  ResponseEntity.ok("USER Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Optional<User> checkUser= userRepository.findByEmail(loginRequest.getEmail());
        if(checkUser.isEmpty()){
            return  ResponseEntity.status(201).body("User is not registered this email id ");
        }
        User user= checkUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return  ResponseEntity.status(401).body("Invalid Password");
        }
        Map<String , Object> claims= new HashMap<>();
        claims.put("role",user.getRole());

        String token = jwtUtil.generateToken(claims,user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));


    }
}
