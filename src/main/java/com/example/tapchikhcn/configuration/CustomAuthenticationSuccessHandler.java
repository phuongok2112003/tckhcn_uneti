package com.example.tapchikhcn.configuration;


import com.example.tapchikhcn.dto.TokenDto;
import com.example.tapchikhcn.entity.UserEntity;
import com.example.tapchikhcn.services.UserService;
import com.example.tapchikhcn.utils.EbsConvertUtils;
import com.example.tapchikhcn.utils.EbsTokenUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserEntity user = (UserEntity) authentication.getPrincipal();

        userService.permanentLock(user.getUsername());

        String accessToken = EbsTokenUtils.createAccessToken(user);
        String refreshToken = EbsTokenUtils.createRefreshToken(user.getUsername());

        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        response.getWriter().write(EbsConvertUtils.toString(tokenDto));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
