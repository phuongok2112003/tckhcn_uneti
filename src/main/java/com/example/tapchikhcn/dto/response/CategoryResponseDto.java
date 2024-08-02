package com.example.tapchikhcn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private int id;
    private String name;
    private Date updateAt;
    private String codeName;
    private Integer type;
}
