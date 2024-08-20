package com.project.demo.rest.user;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.level.Level;

import com.project.demo.logic.entity.level.LevelRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserDTO;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;


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

    @Autowired
    private EmailService emailService;

    private final AuthenticationService authenticationService;

    public userRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole( 'SUPER_ADMIN')")
    public List<User> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        return UserRepository.findAllExcludingAuthenticatedUser(authenticatedUser.getId());
    }


    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        user.setExperience(10L);
        user.setFoodUser(10L);
        user.setCleanerUser(10L);
        user.setKid(false);
        user.setPrivateCode(null);
        user.setVisible(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optionalRole = RoleRepository.findByName(RoleEnum.BASE);
        Optional<User> optionalUser = UserRepository.findByEmail(user.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return null;
        }
        Optional<Level> optionalLevel = levelRepository.findByName("Level 1");
        if (optionalLevel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Default level 'Level_1' not found");
        }

        user.setLevel(optionalLevel.get());

        user.setRole(optionalRole.get());
        User savedUser = UserRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/email/{id}")
    public String sendEmailUser (@PathVariable Long id, @RequestBody User user) throws IOException {
        Random random = new Random();
        int min = 100000;
        int max = 999999;

        int privateCode = random.nextInt(max - min + 1) + min;


        UserRepository.findById(id)
                .map(existingUser -> {

                    existingUser.setPrivateCode(privateCode);

                    return UserRepository.save(existingUser);

                })
                .orElseGet(() -> {
                    user.setId(id);
                    return UserRepository.save(user);
                });

        return emailService.sendTextEmail(user.getEmail(), privateCode);
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
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ASSOCIATE', 'BASE')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userSpec) {

        return UserRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userSpec.getName());
                    if (userSpec.getFoodUser() != null) {
                        existingUser.setFoodUser(userSpec.getFoodUser());
                    }
                    if (userSpec.getCleanerUser() != null) {
                        existingUser.setCleanerUser(userSpec.getCleanerUser());
                    }
                    User updatedUser = UserRepository.save(existingUser);
                    UserDTO updatedUserSpec = new UserDTO();
                    updatedUserSpec.setId(updatedUser.getId());
                    updatedUserSpec.setName(updatedUser.getName());
                    updatedUserSpec.setFoodUser(updatedUser.getFoodUser());
                    updatedUserSpec.setCleanerUser(updatedUser.getCleanerUser());
                    return ResponseEntity.ok(updatedUserSpec);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PutMapping("/profile/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ASSOCIATE', 'BASE')")
    public User updateProfileUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User authenticatedUserProfile = authenticationService.authenticateId(user);

        return UserRepository.findById(id)
                .map(existingUser -> {
                    if (authenticatedUserProfile == null) {
                        existingUser.setId(id);
                        return UserRepository.save(existingUser);
                    }
                    existingUser.setName(user.getName());
                    existingUser.setLastname(user.getLastname());
                    existingUser.setEmail(user.getEmail());

                    if (user.getFoodUser() != null) {
                        existingUser.setFoodUser(user.getFoodUser());
                    }
                    if (user.getCleanerUser() != null) {
                        existingUser.setCleanerUser(user.getCleanerUser());
                    }

                    return UserRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return UserRepository.save(user);
                });
    }

    @PutMapping("/kid/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ASSOCIATE', 'BASE')")
    public User updateKidStatus(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User authenticatedUserProfile = authenticationService.authenticateId(user);

        return UserRepository.findById(id)
                .map(existingUser -> {

                    if (authenticatedUserProfile == null){
                        existingUser.setId(id);
                        return UserRepository.save(existingUser);
                    }

                    existingUser.setKid(!existingUser.isKid());


                    return UserRepository.save(existingUser);

                })
                .orElseGet(() -> {
                    user.setId(id);
                    return UserRepository.save(user);
                });
    }

    @PutMapping("/associate/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ASSOCIATE', 'BASE')")
    public User updateAssociateUser(@PathVariable Long id, @RequestBody User user) {
        return UserRepository.findById(id)
                .map(existingUser -> {
                    if (!Objects.equals(existingUser.getPrivateCode(), user.getPrivateCode())) {
                        existingUser.setId(id);
                        return UserRepository.save(existingUser);
                    }
                    existingUser.setName(user.getName());
                    existingUser.setLastname(user.getLastname());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setFoodUser(user.getFoodUser());
                    existingUser.setCleanerUser(user.getCleanerUser());

                    Optional<Role> optionalRole = RoleRepository.findByName(RoleEnum.ASSOCIATE);

                    if (optionalRole.isEmpty()) {
                        return null;
                    }

                    existingUser.setRole(optionalRole.get());

                    return UserRepository.save(existingUser);

                })
                .orElseGet(() -> {
                    user.setId(id);
                    return UserRepository.save(user);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = UserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVisible(false);
            UserRepository.save(user);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}