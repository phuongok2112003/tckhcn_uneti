package com.example.tapchikhcn.dto.response;

import com.example.tapchikhcn.dto.request.CommentRequestDto;
import com.example.tapchikhcn.dto.request.PostRequestDto;
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
public class UserResponseDto {
    private String username;
    private String email;
    private String password;
    private String permission ;
    private String verifyToken;
    private boolean isActive;
    private String forgotToken;
    private Date forgotTokenExpire;
    private Date createdAt;
    private Date updatedAt;
    private List<PostResponseDto> posts;
    private List<CommentResponseDto> comments;
}
