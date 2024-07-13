package com.dobudobu.newsapiapp.Util.AuthUtils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEmailUtil {

    public String getEmailAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
