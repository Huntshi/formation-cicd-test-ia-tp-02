package com.devops.cicd.user;

import com.devops.cicd.PasswordPolicy;

public class User {

    private final String email;
    private final String password;
    private final Role role;

    public User(String email, String password, Role role) {

        if(email == null) {
            throw new IllegalArgumentException("email must be valid");
        }

        String emailTrimmed = email.trim();
        if(emailTrimmed.isEmpty()) {
            throw new IllegalArgumentException("email must be valid");
        }

        String arrobase = "@";
        if(emailTrimmed.startsWith(arrobase) || emailTrimmed.endsWith(arrobase)) {
            throw new IllegalArgumentException("email must be valid");
        }

        int atCount = 0;
        for (int i = 0; i < emailTrimmed.length(); i++) {
            if (emailTrimmed.charAt(i) == '@') {
                atCount++;
            }
        }

        if (atCount != 1) {
            throw new IllegalArgumentException("email must be valid");
        }

        int index = emailTrimmed.indexOf(arrobase);

        if(emailTrimmed.indexOf('.', index + 1) == -1) {
            throw new IllegalArgumentException("email must be valid");
        }

        // Vérification du password
        if (password == null) {
            throw new IllegalArgumentException("password must be strong");
        }

        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("password must be strong");
        }

        if(!PasswordPolicy.isStrong(password)) {
            throw new IllegalArgumentException("password must be strong");
        }

        // Vérification du role

        if(role == null) {
            throw new IllegalArgumentException("role must not be null");
        }

        this.email = emailTrimmed;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean canAccessAdminArea() {
        if (role == Role.ADMIN) {
            return true;
        }
        return false;
    }

    // BONUS: vous pouvez ajouter equals/hashCode/toString si utile (non obligatoire)
}
