# AI NOTES

## Outils IA Utilisé

J'ai utilisé le chat Copilot Pro directement accessible via Github et le modèle était Chat Gpt5-mini.


## Prompt IA Utilisé

1. Prompt initial
   Tu travailles dans un projet Java Maven et de nouvelles fonctionnalités doivent être développées autour de la gestion d’utilisateurs.
A partir du fichier de spécifications founit tu dois générer des cas niminaux, des cas limites et des cas d'erreurs afin que cela corresponde aux règles métiers.
Tu implémenteras aussi la méthode canAccessAdminArea(), la gestion des erreurs (exception), le test fonctionnel sur UserService.register(). Attention, ne code pas l'implémentation Java car je suis le modèle TDD.
Tout ses tests devront etre écrit et rangé correctement dans la logique de l'arborescence actuelle.

2. Prompts améliorés
Aucun


## Comparaison des tests avec et sans IA

1. Sans IA
public boolean canAccessAdminArea(User user){

    if(user.equals("ADMIN")){
        returnn true
    }

    return false
}


Cas nominaux :
email_is_correct()
password_is_correct()
role_is_correct()

Cas limites :
email_has_two_point()
email_is_bigger_that_twenty-four_caracteres()

password_has_height_caracteres()

Cas d'erreur :
email_is_null()
email_is_blank()
email_has_two_@()
email_has_no_point()
email_has_no_@()
email_start_with_@()

password_is_null()
password_is_blank()
password_does_not_contains_special_caractere()
password_does_not_contains_a_minus_case()
password_does_not_contains_a_upper_case()
password_does_not_contain_a_number()
password_is_under_height_caracteres()

role_is_null()
role_is_different_from_USER_or_ADMIN()

Other :
user_canAccessAdminArea_method_is_correct()

userService_create_user_is_correct()

2. Avec IA

```bash
package com.example.user;

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
```
```bash
package com.example.user;

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

        User created = service.register(email, password, role);

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
```

## Cas de tests proposés par l’IA

1. Liste structurée
Début avec les cas minimaux dont fonctionnel et nécéssaire à la bonne validation du code. 
Un cas test au limite pour le mot de passe.
Plusieurs tests d'erreur pour vérifier tous les cas qui sont censés être faux.

2. Classification (nominal / limite / erreur)

Nominal :
shouldCreateUser_whenDataIsValid_andTrimEmail()
shouldAllowAdminAccess_whenRoleIsAdmin()
shouldDenyAdminAccess_whenRoleIsUser()
registerShouldReturnUser_whenInputIsValid()
registerShouldPropagate_whenEmailInvalid()
registerShouldPropagate_whenPasswordWeak()
registerShouldPropagate_whenRoleNull()

Limite :
shouldAcceptPassword_ofLength8_whenMeetsAllPolicyRequirements()

Erreur :
shouldThrow_whenEmailIsEmpty()
shouldThrow_whenEmailIsWhitespaceOnly()
shouldThrow_whenEmailMissingAtSign()
shouldThrow_whenEmailHasNoLocalOrDomainParts()
shouldThrow_whenEmailHasNoDotAfterAt()
shouldThrow_whenEmailHasMultipleAts()
shouldThrow_whenPasswordIsNull()
shouldThrow_whenPasswordIsWhitespaceOnly()
shouldThrow_whenPasswordIsTooWeak()

## Analyse critique

1. Tests conservés
J'ai décidé de conserver tous les tests proposés car cela correspondait à ce que j'avais prévu lors de l'analyse des specs.
2. Tests rejetés
Aucun
3. Décisions humaines
C'est surtout au niveau de l'implémentation de la classe User que j'ai ajouté toutes les levées d'exception nécéssaires
afin de gerer tous les cas possibles et d'obtenir une création d'utilisateur correspondant au spécifications.