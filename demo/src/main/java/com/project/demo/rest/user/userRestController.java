package com.project.demo.rest.user;

import com.project.demo.logic.entity.level.Level;

import com.project.demo.logic.entity.level.LevelRepository;
import com.project.demo.logic.entity.level.LevelUserEnum;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class userRestController {
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private RoleRepository RoleRepository;
    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<User> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        return UserRepository.findAllExcludingAuthenticatedUser(authenticatedUser.getId());
    }


    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody User user) {

        user.setExperience(10L);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optionalRole = RoleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = UserRepository.findByEmail(user.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return null;
        }
        Optional<Level> optionalLevel = levelRepository.findByName(LevelUserEnum.valueOf("Level_1"));
        if (optionalLevel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Default level 'Level_1' not found");
        }

        user.setLevel(optionalLevel.get());

        user.setRole(optionalRole.get());
        User savedUser = UserRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return UserRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/filterByName/{name}")
    public List<User> getUserById(@PathVariable String name) {
        return UserRepository.findUsersWithCharacterInName(name);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'USER')")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return UserRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setLastname(user.getLastname());
                    existingUser.setEmail(user.getEmail());
                    return UserRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return UserRepository.save(user);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        UserRepository.deleteById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}