package com.devops.cicd.user;

public class UserService {

    /**
     * Enregistre un utilisateur à partir des paramètres.
     *
     * Règles (voir spec) :
     * - crée un User
     * - renvoie l'utilisateur créé
     * - propage les erreurs si les données sont invalides
     */
    public UserBuilder register(String email, String password, Role role) {
        UserBuilder userBuilder = new UserBuilder();

        userBuilder.setEmail(email);
        userBuilder.setPassword(password);
        userBuilder.setRole(role);

        return userBuilder;
    }
}
