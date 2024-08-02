package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.entity.UserEntity;
import com.example.tapchikhcn.error.CommonStatus;
import com.example.tapchikhcn.error.UserStatus;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.mapper.UserMapper;
import com.example.tapchikhcn.repository.UserRepository;
import com.example.tapchikhcn.services.UserService;
import com.example.tapchikhcn.utils.EbsSecurityUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
//    private final UserMapper userMapper;
    @Override
    public UserEntity getUserByUsername(String username) {
        if (!StringUtils.hasText(username))
            throw new EOException(UserStatus.USERNAME_IS_EXIST);

        UserEntity user = userRepository.findByUsername(username);
        if (null == user) {
            throw new EOException(CommonStatus.ACCOUNT_NOT_FOUND);
        }
        return user;
    }

    @Override
    public boolean permanentLock(String username) {
        return false;
    }

    @Override
    public UserResponseDto getInfo() {
        UserEntity user = userRepository.findByUsername(EbsSecurityUtils.getUsername());
        if (null == user) {
            throw new EOException(CommonStatus.ACCOUNT_NOT_FOUND);
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ProviderNotFoundException(CommonStatus.ACCOUNT_NOT_FOUND.getMessage());
        }


        return user;
    }
}
