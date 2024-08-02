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
public class FileRequestDto {
    private int id;
    private String name;
    private String file;
    private PostRequestDto post;
    private Date createdAt;
    private Integer catId;
}
