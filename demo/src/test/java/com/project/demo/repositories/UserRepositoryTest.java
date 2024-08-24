package com.project.demo.repositories;


import com.project.demo.logic.entity.level.Level;
import com.project.demo.logic.entity.level.LevelRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import jakarta.annotation.sql.DataSourceDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:mariadb://localhost:3369/classdb"})
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LevelRepository levelRepository;


    @Test
    public void  UserRepository_GetByEmail_ReturnUser(){
        User user = new User();
        user.setName("David");
        user.setLastname("Huertas");
        user.setEmail("dhuertasl@ucenfotec.ac.cr");
        user.setExperience(10L);
        user.setFoodUser(10L);
        user.setCleanerUser(10L);
        user.setKid(false);
        user.setPrivateCode(null);
        user.setVisible(true);
        user.setPassword("ASS123");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.BASE);
        Optional<Level> optionalLevel = levelRepository.findByName("Level 1");

        optionalRole.ifPresent(user::setRole);
        optionalLevel.ifPresent(user::setLevel);

        User savedUser = userRepository.save(user);

        Optional<User> optionalUser = userRepository.findByEmail(savedUser.getEmail());

        User retrievedUser = optionalUser.get();

        Assertions.assertNotNull(retrievedUser);
        Assertions.assertNotEquals(savedUser.getId(), 0);
    }

    @Test
    public void  UserRepository_GetExcAuthUser_ReturnUser(){
        User user = new User();
        user.setName("David");
        user.setLastname("Huertas");
        user.setEmail("dhuertasl@ucenfotec.ac.cr");
        user.setExperience(10L);
        user.setFoodUser(10L);
        user.setCleanerUser(10L);
        user.setKid(false);
        user.setPrivateCode(null);
        user.setVisible(true);
        user.setPassword("ASS123");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.BASE);
        Optional<Level> optionalLevel = levelRepository.findByName("Level 1");

        optionalRole.ifPresent(user::setRole);
        optionalLevel.ifPresent(user::setLevel);

        User savedUser = userRepository.save(user);

        List<User> listUsers = userRepository.findAllExcludingAuthenticatedUser(savedUser.getId());


        Assertions.assertNotNull(listUsers);
        Assertions.assertNotEquals(listUsers.size(), 0);
    }
}
