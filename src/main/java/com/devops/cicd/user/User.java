package com.devops.cicd.user;

public final class User {

    private String email;
    private String password;
    private Role role;

    // Constructeur direct via UserBuilder
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
