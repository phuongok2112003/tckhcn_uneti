package com.example.tapchikhcn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDto {
    private int id;
    private String fullname;
    private String email;
    private String url;
    private String image;
}
