package com.asoto.keycloak.KeycloakBoilerplate.model;

import lombok.Data;
//import org.springframework.data.jpa.domain.AbstractPersistable;
//import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Document(collection = "userextras") for mongo
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;
    private String avatar;


}
