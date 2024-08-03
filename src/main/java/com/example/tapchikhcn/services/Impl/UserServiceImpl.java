package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.configuration.JwtTokenBlacklist;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.UserRequestDto;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;

import static com.example.tapchikhcn.constans.ErrorCodes.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenBlacklist tokenBlacklist;
    @Override
    public UserResponseDto getUserDtoByUsername(String username) {
        UserEntity user = userRepository.findByUsername(EbsSecurityUtils.getUsername());
        if (null == user) {
            throw new EOException(CommonStatus.ACCOUNT_NOT_FOUND);
        }

        return this.entityToDto(user);
    }

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
    public void logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            throw new EOException(UserStatus.IS_NOT_TOKEN);
        }

        String token = header.substring(7);
        if (tokenBlacklist.isTokenExpired(token)) {
            throw new EOException(UserStatus.TOKEN_IS_EXPIRED);
        }

        tokenBlacklist.add(token);
    }

    @Override
    public UserResponseDto save(UserRequestDto dto) {
        dto.setUsername(dto.getUsername());
        this.validateDto(dto);

        UserEntity user = new UserEntity();
        user= this.dtoToEntiy(dto);
        user = userRepository.save(user);
        return entityToDto(user);

    }

    @Override
    public UserResponseDto update(@NonNull int id,@NonNull UserRequestDto dto) {
        UserEntity user=userRepository.findById(id).orElseThrow(() -> new EOException(ENTITY_NOT_FOUND,
                MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        user= this.dtoToEntiy(dto);
        user = userRepository.save(user);
        return entityToDto(user);
    }

    @Override
    public Boolean delete(int  id) {
     try {
         userRepository.deleteById(id);
         return  true;
     }
       catch ( Exception e){
           log.warn(e.getMessage());
           return false;
       }

    }

    private void validateDto(UserRequestDto dto) {
        if (!StringUtils.hasText(dto.getUsername())) {
            throw new EOException(UserStatus.USERNAME_IS_EMPTY);
        }

        if (userRepository.findByUsername(dto.getUsername())!=null) {
            throw new EOException(UserStatus.USERNAME_IS_EXIST);
        }


        if (!StringUtils.hasText(dto.getPassword())) {
            throw new EOException(UserStatus.PASSWORD_IS_EMPTY);
        }
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
        dto.setId(entity.getId());
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
    public UserEntity dtoToEntiy(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(null); // Để bỏ qua trường password
        entity.setPermission(dto.getPermission());
        entity.setVerifyToken(dto.getVerifyToken());
        entity.setActive(dto.isActive());
        entity.setForgotToken(dto.getForgotToken());
        entity.setForgotTokenExpire(dto.getForgotTokenExpire());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

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

        return entity;
    }
}
