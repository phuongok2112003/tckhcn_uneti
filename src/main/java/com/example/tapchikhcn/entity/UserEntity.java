package com.example.tapchikhcn.entity;

import com.example.tapchikhcn.constans.enums.UserPermision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserEntity implements UserDetails {
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

    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<UserPermision> permission ;

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
    private Set<PostEntity> posts;

    @OneToMany(mappedBy = "user")
    private Set<CommentEntity> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>(this.permission);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}