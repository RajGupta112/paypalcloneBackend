package com.paypal.user_service.service;

import com.paypal.user_service.entity.User;
import com.paypal.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplimentation implements   UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        User user1= new User();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());

        User savedUser= userRepository.save(user1);

        return savedUser;

    }

    @Override
    public Optional<User> getUserById(Long id) {


        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
