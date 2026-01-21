package com.devops.cicd;

import com.devops.cicd.user.Role;

public interface Builder {

    void setEmail(String email);
    void setPassword(String password);
    void setRole(Role role);
}
