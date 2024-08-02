package com.example.tapchikhcn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private int id;
    private String name;
    private String url;
    private MenuRequestDto parent;
    private Date createdAt;
    private Date updatedAt;
}
