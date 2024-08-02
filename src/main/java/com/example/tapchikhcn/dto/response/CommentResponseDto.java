package com.example.tapchikhcn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private int id;
    private UserResponseDto user;
    private String comment;
    private PostResponseDto post;
    private String status = "unseen";
    private Date createdAt;
    private Date updatedAt;
}
