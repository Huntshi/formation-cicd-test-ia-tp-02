package com.devops.cicd.user;

import com.devops.cicd.PasswordPolicy;

public final class User {

    private String email;
    private String password;
    private Role role;

    private UserBuilder builder = new UserBuilder();

    // Constructeur privé pour créer à partir d'un UserBuilder
    private User(UserBuilder builder){
        this.email = builder.getEmail();
        this.password = builder.getPassword();
        this.role = builder.getRole();
    }
    // Optionnel : constructeur direct qui valide via UserBuilder (réutilise la logique)
    public User(String email, String password, Role role) {
        UserBuilder b = new UserBuilder();
        b.setEmail(email);
        b.setPassword(password);
        b.setRole(role);
        this.email = b.getEmail();
        this.password = b.getPassword();
        this.role = b.getRole();
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
