package com.example.tapchikhcn.services.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.tapchikhcn.configuration.JwtTokenBlacklist;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.constans.enums.Variables;
import com.example.tapchikhcn.dto.TokenDto;
import com.example.tapchikhcn.dto.request.PasswordResetRequest;
import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.dto.search.EntiySearch;
import com.example.tapchikhcn.entity.UserEntity;
import com.example.tapchikhcn.error.CommonStatus;
import com.example.tapchikhcn.error.UserStatus;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.services.EmailService;
import com.example.tapchikhcn.services.mapper.UserMapper;
import com.example.tapchikhcn.repository.UserRepository;
import com.example.tapchikhcn.services.UserService;
import com.example.tapchikhcn.utils.EbsSecurityUtils;
import com.example.tapchikhcn.utils.EbsTokenUtils;
import com.example.tapchikhcn.utils.PageUtils;
import com.example.tapchikhcn.utils.RenderCodeTest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.function.EntityResponse;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.tapchikhcn.constans.ErrorCodes.ENTITY_NOT_FOUND;
import static com.example.tapchikhcn.constans.ErrorCodes.ERROR_CODE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenBlacklist tokenBlacklist;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    @Override
    public UserResponseDto getUserDtoByUsername(String username) {
        UserEntity user = userRepository.findByEmail(EbsSecurityUtils.getEmail());
        if (null == user) {
            throw new EOException(CommonStatus.ACCOUNT_NOT_FOUND);
        }

        return this.entityToDto(user);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        if (!StringUtils.hasText(username))
            throw new EOException(UserStatus.USERNAME_IS_EXIST);

        UserEntity user = userRepository.findByEmail(username);
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
    public Page<UserResponseDto> searchBy(EntiySearch search) {

        Pageable pageable = PageUtils.getPageable(search.getPageIndex(), search.getPageSize());
        Page<UserEntity> entityList=  userRepository.findAll(pageable);
        List<UserResponseDto> userResponseDtos=entityList.stream().map(this::entityToDto).collect(Collectors.toList());
        return new PageImpl<>(userResponseDtos,pageable,entityList.getTotalElements());
    }

    @Override
    public String sendPasswordResetCode(String email) {
        String code= RenderCodeTest.setValue();
        UserEntity user=userRepository.findByEmail(email);
        if(user==null){
            throw new EOException(ENTITY_NOT_FOUND,
                    MessageCodes.EMAIL_NOT_FOUND, String.valueOf(email));
        }
        user.setForgotToken(EbsTokenUtils.createCode(code,email));
        userRepository.save(user);
        emailService.sendEmail(email,"Password Reset Request",code);
        return code;
    }

    @Override
    public Boolean verifyPasswordResetCode(PasswordResetRequest passwordResetRequest) {
        UserEntity user=userRepository.findByEmail(passwordResetRequest.getEmail());
        if(user==null){
            throw new EOException(ENTITY_NOT_FOUND,
                    MessageCodes.EMAIL_NOT_FOUND, String.valueOf(passwordResetRequest.getEmail()));
        }
        Algorithm algorithm = Algorithm.HMAC256(Variables.SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(user.getForgotToken());

        String emailVerify = decodedJWT.getSubject();
        String codeVerify = decodedJWT.getClaim("code").asString();
        String pasword1=passwordResetRequest.getPassword();
        String pasword2=passwordResetRequest.getPassword2();
        if(emailVerify.equals(passwordResetRequest.getEmail())&&codeVerify.equals(passwordResetRequest.getCode())&&pasword1.equals(pasword2)){
          user.setPassword(bCryptPasswordEncoder.encode(pasword1));
          userRepository.save(user);
          return true;
        }
        else{
            throw new EOException(ERROR_CODE,
                    MessageCodes.USER_NOT_VERIFY,emailVerify);
        }

    }

    @Override
    public TokenDto refreshToken(String refreshToken) {
        Algorithm algorithm = Algorithm.HMAC256(Variables.SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        UserEntity user = userRepository.findByEmail(username);

        if (null == user) {
            throw new EOException(CommonStatus.ACCOUNT_NOT_FOUND);
        }

        String accessToken = EbsTokenUtils.createAccessToken(user);
        return new TokenDto(accessToken, refreshToken);
    }

    @Override
    public UserResponseDto getInfo() {
        UserEntity user = userRepository.findByEmail(EbsSecurityUtils.getEmail());
        if (null == user) {
            throw new EOException(CommonStatus.ACCOUNT_NOT_FOUND);
        }

        return this.entityToDto(user);
    }

    @Override
    public Boolean logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            throw new EOException(UserStatus.IS_NOT_TOKEN);
        }

        String token = header.substring(7);
        if (tokenBlacklist.isTokenExpired(token)) {
            throw new EOException(UserStatus.TOKEN_IS_EXPIRED);
        }

        tokenBlacklist.add(token);
        return true;
    }

    @Override
    public UserResponseDto save(UserRequestDto dto) {
        dto.setUsername(dto.getUsername());
        this.validateDto(dto);

        UserEntity user = new UserEntity();
     this.dtoToEntiy(dto,user);
        user = userRepository.save(user);
        return entityToDto(user);

    }

    @Override
    public UserResponseDto update(@NonNull int id,@NonNull UserRequestDto dto) {
        UserEntity entity=userRepository.findById(id).orElseThrow(() -> new EOException(ENTITY_NOT_FOUND,
                MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPermission(dto.getPermission());
        entity.setActive(dto.isActive());
        entity.setUpdatedAt(new Date());
       userRepository.save(entity);
        return entityToDto(entity);
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

        if (userRepository.findByEmail(dto.getUsername())!=null) {
            throw new EOException(UserStatus.USERNAME_IS_EXIST);
        }


        if (!StringUtils.hasText(dto.getPassword())) {
            throw new EOException(UserStatus.PASSWORD_IS_EMPTY);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
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
    public void dtoToEntiy(UserRequestDto dto, UserEntity entity) {


//          entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword())); // Để bỏ qua trường password
        entity.setPermission(dto.getPermission());
        entity.setVerifyToken(dto.getVerifyToken());
        entity.setActive(dto.isActive());
        entity.setForgotToken(dto.getForgotToken());
        entity.setForgotTokenExpire(dto.getForgotTokenExpire());
        entity.setCreatedAt(new Date());
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


    }
}
