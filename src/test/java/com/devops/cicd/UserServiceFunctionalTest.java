package com.devops.cicd;

import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import com.devops.cicd.user.UserBuilder;
import com.devops.cicd.user.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests fonctionnels légers pour UserService.register(...)
 *
 * Hypothèses :
 * - Classe UserService disponible avec méthode :
 *     User register(String email, String password, Role role)
 * - register doit créer et retourner l'utilisateur valide
 * - register doit propager IllegalArgumentException avec les messages spécifiés si les données sont invalides
 */
public class UserServiceFunctionalTest {

    @Test
    public void registerShouldReturnUser_whenInputIsValid() {
        UserService service = new UserService();
        String email = "  bob.smith@company.io  ";
        String password = "Abcdef1!";
        Role role = Role.USER;

        UserBuilder created = service.register(email, password, role);

        assertNotNull(created);
        assertEquals("bob.smith@company.io", created.getEmail(), "email must be trimmed on creation");
        assertEquals(password, created.getPassword(), "password must be stored as-is");
        assertEquals(role, created.getRole());
    }

    @Test
    public void registerShouldPropagate_whenEmailInvalid() {
        UserService service = new UserService();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.register("alice", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex.getMessage());
    }

    @Test
    public void registerShouldPropagate_whenPasswordWeak() {
        UserService service = new UserService();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.register("alice@test.com", "weak", Role.USER));
        assertEquals("password must be strong", ex.getMessage());
    }

    @Test
    public void registerShouldPropagate_whenRoleNull() {
        UserService service = new UserService();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.register("alice@test.com", "Abcdef1!", null));
        assertEquals("role must not be null", ex.getMessage());
    }
}