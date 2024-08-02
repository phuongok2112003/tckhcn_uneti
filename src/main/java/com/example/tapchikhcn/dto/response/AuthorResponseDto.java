package com.example.tapchikhcn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
    private int id;
    private String fullname;
    private String email;
    private String url;
    private String image;
}
