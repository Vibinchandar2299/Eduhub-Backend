package com.example.eduhub_backend.course;

import com.example.eduhub_backend.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;    
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;   
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;


@RestController
public class UserController {
    
    private static List<User> userList = new ArrayList<>();
    
    static {
        userList.add(new User("U001", "john_doe", "password123"));
        userList.add(new User("U002", "jane_smith", "pass456"));
        userList.add(new User("U003", "mike_wilson", "secure789"));
        userList.add(new User("U004", "sarah_jones", "mypass321"));
        userList.add(new User("U005", "david_brown", "pwd654"));
    }
    
    @GetMapping("/search/get-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUserByPathVariable(@PathVariable("userId") String userId) {
        validateUserId(userId);
        
        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                return ResponseEntity.ok(user);
            }
        }
        throw new ResourceNotFoundException("User", "userId", userId);
    }
    
    @GetMapping("/get-user")
    public ResponseEntity<User> getUserByRequestParam(@RequestParam("userId") String userId) {
        validateUserId(userId);
        
        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                return ResponseEntity.ok(user);
            }
        }
        throw new ResourceNotFoundException("User", "userId", userId);
    }
    
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userList.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<User> updateUserPassword(@PathVariable("userId") String userId, @RequestBody User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId().equals(userId)) {
                User user = userList.get(i);
                user.setPassword(updatedUser.getPassword());
                return ResponseEntity.ok(user);
            }
        }
        throw new ResourceNotFoundException("User", "userId", userId);
    }
    
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) throws HttpRequestMethodNotSupportedException {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId().equals(userId)) {
                userList.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        throw new HttpRequestMethodNotSupportedException("DELETE");
    }
    
    private void validateUserId(String userId) {
        if (userId.matches(".*[^a-zA-Z0-9].*")) {
            throw new IllegalArgumentException("UserId contains special characters. Only alphanumeric characters are allowed.");
        }
    }
}