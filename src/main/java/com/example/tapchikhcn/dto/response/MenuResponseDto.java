package com.example.tapchikhcn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
    private int id;
    private String name;
    private String url;
    private MenuResponseDto parent;
    private Date createdAt;
    private Date updatedAt;
}
