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
public class FileResponseDto {
    private int id;
    private String name;
    private String file;
    private PostResponseDto post;
    private Date createdAt;
    private int catId;
}
