package com.example.tapchikhcn.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSearch extends  SearchDto{
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
