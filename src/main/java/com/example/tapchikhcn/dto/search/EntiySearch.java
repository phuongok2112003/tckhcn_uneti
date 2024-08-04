package com.example.tapchikhcn.dto.search;

import com.example.tapchikhcn.dto.response.CommentResponseDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntiySearch extends  SearchDto{
    private int id;
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

}
