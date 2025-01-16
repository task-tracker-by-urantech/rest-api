package com.urantech.restapi.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Task {
    private long id;
    @NonNull
    private String description;
    private long userId;
    private boolean done = false;

    public Task(@NonNull String description, Long userId) {
        this.description = description;
        this.userId = userId;
    }
}
