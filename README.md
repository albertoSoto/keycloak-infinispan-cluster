# keycloak-infinispan-cluster

This project includes a basic configuration with KC 17 and infinispan
KC Persistance does not rely on Mysql (yet)

To start the project:

> docker-compose up -d --build

Infinispan 1, will be available at:
http://localhost:11222/
u:user
p:pass
> Check that all KC cache repositories are created

Keycloak 1, will be available at:
http://localhost:8080/auth
u:admin
p:admin
> Check that basic realm is generated (test realm)
> Check that user test exist in realm (test/test - test@test.com)

Realm file located at keycloak/realm.json

## Aplication to connect

Under /app-springboot you have a spring boot application that needs it´s own realm
The project needs JDK11

