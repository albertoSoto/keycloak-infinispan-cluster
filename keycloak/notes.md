## Concepts for KC

### Client
Clients are entities that can request authentication of a user.
represents a web application or web service that wants to use Keycloak to authenticate and authorize users.

### Realm: Client scopes

Protocol mappers links that gets included in all clients (services connected)

### Realm: Mappers

This are attributtes that can be included in the token, 
which means is information that will be recorded and retrieved with the platform that
logs in

### SPI

Example https://github.com/akoserwal/keycloak-integrations/tree/master/keycloak-spi-kafka
https://medium.com/keycloak/keycloak-event-listener-spi-publish-to-kafka-db1fabd285c1

Adds custom functionality
an SPI for Keycloak we need to basically implement two interfaces:
- Implement: EventListenerProvider

``
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
public class KeycloakCustomEventListener implements EventListenerProvider {
@Override
public void onEvent(Event event) {
System.out.println("Event:-"+event.getUserId());
Producer.publishEvent(event.getType().toString(), event.getUserId());
}
@Override
public void onEvent(AdminEvent adminEvent, boolean b) {
System.out.println("Admin Event:-"+adminEvent.getResourceType().name());
Producer.publishEvent(adminEvent.getOperationType().toString(), adminEvent.getAuthDetails().getUserId());
}
@Override
public void close() {
}
}
``

- Implement: EventListenerProviderFactory

``
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
public class KeycloakCustomEventListenerProviderFactory implements EventListenerProviderFactory {
@Override
public EventListenerProvider create(KeycloakSession keycloakSession) {
return new KeycloakCustomEventListener();
}
@Override
public void init(Config.Scope scope) {
}
@Override
public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
}
@Override
public void close() {
}
@Override
public String getId() {
    return "kafka-event-listener";
}
}
``

- Add /src/main/resources/META-INF/services/org.keycloak.events.EventListenerProviderFactory

``
io.github.akoserwal.KeycloakCustomEventListenerProviderFactory
``

### event listeners

When we implement an SPI we specify the event listener which is the entry point for it
There are more than 100 types of event listeners
https://www.keycloak.org/docs-api/11.0/javadocs/org/keycloak/events/EventType.html

For sending an email when the user register, there is this tutorial 
https://aboutbits.it/blog/2020-11-23-keycloak-custom-event-listener

Events can be registered with the Events option per Realm

Check source code at https://github.com/aboutbits/keycloak-extensions

### theme

https://medium.com/@sidsamanta/theming-keycloak-pages-4dfc2b2b0b21
An example of a theme is located at https://github.com/lukin/keywind

Only Css adapter:

https://dev.to/austincunningham/create-a-custom-theme-for-keycloak-15ji
https://github.com/austincunningham/raincatcher-keycloak-theme

### AuthenticatorFactory

Ejemplo: facebook-remember-me
Keycloak: Custom Authentication Flows
https://www.youtube.com/watch?v=u36QK9oyrtM

It just allows user to loggin or authenticate in another way

An example for an ip authenticator is the following
https://github.com/lukaszbudnik/keycloak-ip-authenticator

As a recap:

- We need to implement Authenticator interface
  https://github.com/lukaszbudnik/keycloak-ip-authenticator/blob/main/src/main/java/com/github/lukaszbudnik/keycloak/ipauthenticator/IPAuthenticator.java
- We need to implement the Factory pattern
  https://github.com/lukaszbudnik/keycloak-ip-authenticator/blob/main/src/main/java/com/github/lukaszbudnik/keycloak/ipauthenticator/IPAuthenticatorFactory.java
- We need to register it as a service
  https://github.com/lukaszbudnik/keycloak-ip-authenticator/blob/main/src/main/resources/META-INF/services/org.keycloak.authentication.AuthenticatorFactory

### Authentication flows

https://www.thomasvitale.com/keycloak-authentication-flow-sso-client/
https://www.thomasvitale.com/spring-boot-keycloak-security/
https://github.com/ThomasVitale/spring-keycloak-tutorials
https://www.thomasvitale.com/tag/keycloak/
https://www.thomasvitale.com/spring-security-keycloak/

Standard flow:

1. A user visits the application on a browser and tries to access a protected resource;
2. The application redirects the user to the Keycloak login page;
3. The user enters their username and password;
4. Keycloak authenticates the user;
5. If the authentication succeeds, Keycloak redirects the user to the protected resource of the application.

In the background, Keycloak provides the application with two tokens as defined by the OIDC protocol:

- An Identity Token, which contains information about the logged user such as the username and the email.
- An Access Token, digitally signed by the realm, which contains access data such as the roles assigned to the logged user.
