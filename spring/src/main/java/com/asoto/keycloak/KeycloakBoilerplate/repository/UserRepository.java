package com.asoto.keycloak.KeycloakBoilerplate.repository;

import com.asoto.keycloak.KeycloakBoilerplate.model.UserExtra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserExtra, Long> {

}