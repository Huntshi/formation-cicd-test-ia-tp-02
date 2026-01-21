package com.devops.cicd.user;

import com.devops.cicd.Builder;
import com.devops.cicd.PasswordPolicy;

public class UserBuilder implements Builder {

    private String email;
    private String password;
    private Role role;

    @Override
    public void setEmail(String email) {

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

        this.email = emailTrimmed;
    }

    @Override
    public void setPassword(String password) {

        if (password == null) {
            throw new IllegalArgumentException("password must be strong");
        }

        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("password must be strong");
        }

        if(!PasswordPolicy.isStrong(password)) {
            throw new IllegalArgumentException("password must be strong");
        }

        this.password = password;
    }

    @Override
    public void setRole(Role role) {
        if(role == null) {
            throw new IllegalArgumentException("role must not be null");
        }

        this.role = role;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

}
