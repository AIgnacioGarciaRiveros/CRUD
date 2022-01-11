package com.example.RestService.service;

import com.example.RestService.entity.User;
import com.example.RestService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(String name){
        User user = new User();
        user.setName(name);
        user.setRegister(Boolean.TRUE);
        userRepository.save(user);
    }

    @Transactional
    public void modifyUser(Integer id, String nombre) {
        userRepository.modify(id, nombre);
    }

    @Transactional(readOnly = true)
    public User searchForId(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> showUser() {
        return userRepository.findAll();
    }

}
