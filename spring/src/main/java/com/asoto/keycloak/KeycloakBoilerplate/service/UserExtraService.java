package com.asoto.keycloak.KeycloakBoilerplate.service;

import com.asoto.keycloak.KeycloakBoilerplate.model.UserExtra;

import java.util.List;


public interface UserExtraService {

    // save operation
    UserExtra saveUser(UserExtra userExtra);

    // read operation
    List<UserExtra> fetchUserList();

    // update operation
    UserExtra updateUser(UserExtra userExtra, Long userExtraId);

    // delete operation
    void deleteUserById(Long userExtraId);
}
