package com.attendance.service;

import com.attendance.form.UserForm;
import com.attendance.model.Role;
import com.attendance.model.User;
import com.attendance.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.attendance.service.UserService;

import java.io.IOException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public User createUser(UserForm userForm) {
        User user = mapFormToEntity(userForm);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, UserForm userForm) {
        User user = getUserById(id);
        // Update fields based on the UserForm
        user.setUsername(userForm.getUsername());
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setContactNo(userForm.getContactNo());
        user.setDob(userForm.getDob());
        user.setAddress(userForm.getAddress());
        user.setSalary(userForm.getSalary());
        user.setAadharId(userForm.getAadharId());
        user.setRole(user.getRole());

        // Handle resume upload (if necessary)
        if (userForm.getResume() != null && !userForm.getResume().isEmpty()) {
            try {
                user.setResume(userForm.getResume().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error saving resume file", e);
            }
        }

        // Save the updated user
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    private User mapFormToEntity(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setContactNo(userForm.getContactNo());
        user.setDob(userForm.getDob());
        user.setAddress(userForm.getAddress());
        user.setSalary(userForm.getSalary());
        user.setAadharId(userForm.getAadharId());
        user.setRole(userForm.getJobRole());
        System.out.println(user.getRole());
        user.setPassword(userForm.getPassword());
        // Handle resume upload
        if (userForm.getResume() != null && !userForm.getResume().isEmpty()) {
            try {
                user.setResume(userForm.getResume().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error saving resume file", e);
            }
        }

        return user;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<Role> getUserRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
