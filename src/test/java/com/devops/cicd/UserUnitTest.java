package com.devops.cicd;

import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la validation métier de User et la méthode canAccessAdminArea().
 *
 * Hypothèses :
 * - Constructeur disponible : new User(String email, String password, Role role)
 * - Les validations lèvent IllegalArgumentException avec les messages :
 *   "email must be valid", "password must be strong", "role must not be null"
 * - PasswordPolicy.isStrong(password) est utilisé en production pour vérifier la force du mot de passe.
 */
public class UserUnitTest {

    // ----- Cas valides (minimaux) -----
    @Test
    public void shouldCreateUser_whenDataIsValid_andTrimEmail() {
        String rawEmail = "  alice@test.com  ";
        String password = "Abcdef1!";
        Role role = Role.USER;

        User u = new User(rawEmail, password, role);

        // email doit être trim() avant stockage
        assertEquals("alice@test.com", u.getEmail());
        // password ne doit pas être modifié
        assertEquals(password, u.getPassword());
        // role stocké tel quel
        assertEquals(role, u.getRole());
    }

    @Test
    public void shouldAllowAdminAccess_whenRoleIsAdmin() {
        User admin = new User("admin@test.com", "Abcdef1!", Role.ADMIN);
        assertTrue(admin.canAccessAdminArea());
    }

    @Test
    public void shouldDenyAdminAccess_whenRoleIsUser() {
        User user = new User("user@test.com", "Abcdef1!", Role.USER);
        assertFalse(user.canAccessAdminArea());
    }

    // ----- Cas limites pour le mot de passe -----
    @Test
    public void shouldAcceptPassword_ofLength8_whenMeetsAllPolicyRequirements() {
        // longueur = 8 ; contient majuscule, minuscule, chiffre, caractère spécial
        String password = "Abcdef1!"; // 8 chars
        User u = new User("min@edge.com", password, Role.USER);
        assertEquals(password, u.getPassword());
    }

    // ----- Cas d'erreurs pour l'email -----
    @Test
    public void shouldThrow_whenEmailIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex.getMessage());
    }

    @Test
    public void shouldThrow_whenEmailIsWhitespaceOnly() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("   ", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex.getMessage());
    }

    @Test
    public void shouldThrow_whenEmailMissingAtSign() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("alice", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex.getMessage());
    }

    @Test
    public void shouldThrow_whenEmailHasNoLocalOrDomainParts() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> new User("alice@", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                () -> new User("@test.com", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex2.getMessage());
    }

    @Test
    public void shouldThrow_whenEmailHasNoDotAfterAt() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("alice@test", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex.getMessage());
    }

    @Test
    public void shouldThrow_whenEmailHasMultipleAts() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("alice@@test.com", "Abcdef1!", Role.USER));
        assertEquals("email must be valid", ex.getMessage());
    }

    // ----- Cas d'erreurs pour le mot de passe -----
    @Test
    public void shouldThrow_whenPasswordIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("a@b.c", null, Role.USER));
        assertEquals("password must be strong", ex.getMessage());
    }

    @Test
    public void shouldThrow_whenPasswordIsWhitespaceOnly() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("a@b.c", "   ", Role.USER));
        assertEquals("password must be strong", ex.getMessage());
    }

    @Test
    public void shouldThrow_whenPasswordIsTooWeak() {
        // Exemple : trop court ou sans majuscule/chiffre/special
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("a@b.c", "weakpass", Role.USER));
        assertEquals("password must be strong", ex.getMessage());
    }

    // ----- Cas d'erreurs pour le rôle -----
    @Test
    public void shouldThrow_whenRoleIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("a@b.c", "Abcdef1!", null));
        assertEquals("role must not be null", ex.getMessage());
    }
}