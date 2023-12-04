package com.example.util;

import com.example.entity.User;
import com.example.entity.UserRole;
import com.example.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public class SecurityUtil {


    public static void checkAdmin() {
        User currentUser = getCurrentUser();
        Set<UserRole> roleSet = currentUser.getRoles();
        boolean isAdmin = false;
        for (UserRole role : roleSet) {
            if (Constants.ADMIN_ROLE.equals(role.getName())) {
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin)
            throw new GenericException(HttpStatus.FORBIDDEN, "You don't have admin role, please contact the admin!");
    }

    public static Boolean isAdmin() {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Set<UserRole> roleSet = currentUser.getRoles();
            for (UserRole role : roleSet) {
                if (Constants.ADMIN_ROLE.equals(role.getName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static User getAuthorizedUser() {
        return getCurrentUser();
    }


    private static User getCurrentUser() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new GenericException(HttpStatus.UNAUTHORIZED, "Please login first");
        }

    }

}
