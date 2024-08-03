package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.dto.response.PostResponseDto;
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
    private final UserMapper userMapper;
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

        return this.entityToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ProviderNotFoundException(CommonStatus.ACCOUNT_NOT_FOUND.getMessage());
        }


        return user;
    }
    public UserResponseDto entityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPassword(null); // Để bỏ qua trường password
        dto.setPermission(entity.getPermission().toString());
        dto.setVerifyToken(entity.getVerifyToken());
        dto.setActive(entity.isActive());
        dto.setForgotToken(entity.getForgotToken());
        dto.setForgotTokenExpire(entity.getForgotTokenExpire());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // Mapping các bài viết (posts)
//        if (entity.getPosts() != null) {
//            dto.setPosts(entity.getPosts().stream()
//                    .map(post -> {
//                        PostResponseDto postDto = new PostResponseDto();
//                        postDto.setTitle(post.getTitle());
//                        postDto.setContent(post.getContent());
//                        // Thêm các trường khác nếu có
//                        return postDto;
//                    })
//                    .collect(Collectors.toList()));
//        }
//
//        // Mapping các bình luận (comments)
//        if (entity.getComments() != null) {
//            dto.setComments(entity.getComments().stream()
//                    .map(comment -> {
//                        CommentResponseDto commentDto = new CommentResponseDto();
//                        commentDto.setText(comment.getText());
//                        // Thêm các trường khác nếu có
//                        return commentDto;
//                    })
//                    .collect(Collectors.toList()));
//        }

        return dto;
    }
}
