package com.example.tapchikhcn.constans.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum UserPermision {
    USER("user"),
    ADMIN("admin");


    private String name;

    @JsonCreator
    public static UserPermision parseByCode(@JsonProperty("name") String name) {
        if (name == null) {
            return null;
        }
        for (UserPermision status : values()) {
            if (Objects.equals(status.name, name)) {
                return status;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
