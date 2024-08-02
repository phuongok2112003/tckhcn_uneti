package com.example.tapchikhcn.configuration;



import com.example.tapchikhcn.constans.enums.Variables;
import com.example.tapchikhcn.entity.UserEntity;
import com.example.tapchikhcn.error.CommonStatus;
import com.example.tapchikhcn.error.DataError;
import com.example.tapchikhcn.services.UserService;
import com.example.tapchikhcn.utils.EbsConvertUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final UserService userService;

    public CustomAuthenticationFailureHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (hasException(response, exception))
            return;

        String username = request.getParameter("username");
        UserEntity user = userService.getUserByUsername(username);
        // user != null vì loadUserByUsername đã check, nếu null thì đã có trả ra UsernameNotFoundException

//        byte newFailAttempts = (byte) (user.getFailedAttempt() + 1);
//        userService.increaseFailedAttempts(username, newFailAttempts);
//        if (newFailAttempts > Variables.MAX_FAILED_ATTEMPTS) {
//            int coefficient = newFailAttempts - Variables.MAX_FAILED_ATTEMPTS;
//            userService.temporaryLock(username, coefficient);
//            String message = String.format(CommonStatus.TEMPORARY_LOCK.getMessage(), Variables.LOCK_TIME_DURATION * coefficient);
//            response.getWriter().write(ToStringBuilder.reflectionToString(DataError.build(CommonStatus.TEMPORARY_LOCK.getCode(), message)));
//            return;
//        }

        response.getWriter().write(ToStringBuilder.reflectionToString(DataError.build(CommonStatus.WRONG_USERNAME_OR_PASSWORD)));
    }

    private boolean hasException(HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception.getCause() instanceof ProviderNotFoundException) {
            response.getWriter().write(EbsConvertUtils.toString(DataError.build(CommonStatus.ACCOUNT_NOT_FOUND)));
            return true;
        }

        if (exception.getCause() instanceof LockedException) {
            response.getWriter().write(EbsConvertUtils.toString(DataError.build(CommonStatus.TEMPORARY_LOCK_NOT_FINISH)));
            return true;
        }

        if (exception instanceof LockedException) {
            response.getWriter().write(EbsConvertUtils.toString(DataError.build(CommonStatus.ACCOUNT_HAS_BEEN_LOCKED)));
            return true;
        }

        if (exception instanceof DisabledException) {
            response.getWriter().write(EbsConvertUtils.toString(DataError.build(CommonStatus.ACCOUNT_IS_NOT_ACTIVATED)));
            return true;
        }

        return false;
    }
}