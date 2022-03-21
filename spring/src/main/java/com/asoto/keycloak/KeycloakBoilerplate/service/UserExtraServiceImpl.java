package com.asoto.keycloak.KeycloakBoilerplate.service;

import com.asoto.keycloak.KeycloakBoilerplate.model.UserExtra;
import com.asoto.keycloak.KeycloakBoilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserExtraServiceImpl implements com.asoto.keycloak.KeycloakBoilerplate.service.UserExtraService {

    @Autowired
    private final UserRepository userRepository;

    public UserExtraServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserExtra saveUser(UserExtra userExtra) {
        return userRepository.save(userExtra);
    }

    @Override
    public List<UserExtra> fetchUserList() {
        return (List<UserExtra>) userRepository.findAll();
    }

    @Override
    public UserExtra updateUser(UserExtra userExtra, Long userExtraId) {
        UserExtra depDB = userRepository.findById(userExtraId).get();
        if (Objects.nonNull(userExtra.getUsername()) && !"".equalsIgnoreCase(userExtra.getUsername())) {
            depDB.setUsername(userExtra.getUsername());
        }
        return userRepository.save(depDB);
    }

    @Override
    public void deleteUserById(Long userExtraId) {
        userRepository.deleteById(userExtraId);
    }
}
