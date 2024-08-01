package com.example.tapchikhcn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "permission", nullable = false, columnDefinition = "ENUM('user','admin')")
    private String permission = "user";

    @Column(name = "verify_token", length = 191)
    private String verifyToken;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @Column(name = "forgot_token", length = 191)
    private String forgotToken;

    @Column(name = "forgot_token_expire")
    private Date forgotTokenExpire;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;


}