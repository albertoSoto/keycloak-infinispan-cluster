package com.asoto.keycloak.KeycloakBoilerplate.controller;

import com.asoto.keycloak.KeycloakBoilerplate.model.UserExtra;
import com.asoto.keycloak.KeycloakBoilerplate.service.UserExtraService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class RestApiController {
    // Annotation
    private final UserExtraService userService;

    public RestApiController(UserExtraService userService) {
        this.userService = userService;
    }

    // Save operation
    @PostMapping("/users")
    public UserExtra saveDepartment(@Valid @RequestBody UserExtra user)
    {
        return userService.saveUser(user);
    }

    // Read operation
    @GetMapping("/users")
    public List<UserExtra> fetchUserList()
    {
        return userService.fetchUserList();
    }

    // Update operation
    @PutMapping("/users/{id}")
    public UserExtra updateDepartment(@RequestBody UserExtra user,
                     @PathVariable("id") Long userId)
    {
        return userService.updateUser(user, userId);
    }

    // Delete operation
    @DeleteMapping("/users/{id}")
    public String deleteDepartmentById(@PathVariable("id")
                                               Long userId)
    {
        userService.deleteUserById(userId);
        return "Deleted Successfully";
    }
}
