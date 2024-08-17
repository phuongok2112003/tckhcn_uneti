package com.example.tapchikhcn.utils;



import com.example.tapchikhcn.entity.UserEntity;
import com.example.tapchikhcn.error.CommonStatus;
import com.example.tapchikhcn.exceptions.EOException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class EbsSecurityUtils {
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !Objects.isNull(authentication) && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static Object getPrincipal() {
        if (isAuthenticated()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getPrincipal();
        }

        return null;
    }

    public static String getEmail() {
        if (isAuthenticated()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return ((UserEntity) authentication.getPrincipal()).getEmail();
        }

        throw new EOException(CommonStatus.FORBIDDEN);
    }

    public static UserEntity getCurrentUser() {
        if (isAuthenticated()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (UserEntity) authentication.getPrincipal();
        }

        throw new EOException(CommonStatus.FORBIDDEN);
    }
}
