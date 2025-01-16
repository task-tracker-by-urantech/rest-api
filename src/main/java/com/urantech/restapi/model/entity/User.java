package com.urantech.restapi.model.entity;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class User {
    private long id;
    @NonNull
    private String email;
    @NonNull
    private String password;

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }
}
