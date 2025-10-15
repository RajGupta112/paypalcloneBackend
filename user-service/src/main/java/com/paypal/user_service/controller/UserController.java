package com.paypal.user_service.controller;

import com.paypal.user_service.entity.User;
import com.paypal.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

      private final UserService userService;

      @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser= userService.createUser(user);
        return   ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<User> getUserById(@PathVariable Long id){
          User user= userService.getUserById(id).orElseThrow(()-> new RuntimeException("user not found with this id "+id));
          return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
            List<User> user= userService.getAllUsers();
            return ResponseEntity.ok(user);

    }


}
