package com.example.tapchikhcn.services;

import com.example.tapchikhcn.entity.UserEntity;
import org.springframework.stereotype.Service;


public interface UserService {

    UserEntity getUserByUsername(String username);
    boolean permanentLock(String username);

}
