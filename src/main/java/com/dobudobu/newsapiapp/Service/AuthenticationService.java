package com.dobudobu.newsapiapp.Service;

import com.dobudobu.newsapiapp.Dto.RefreshTokenRequest;
import com.dobudobu.newsapiapp.Dto.Response.JwtAuthenticationResponse;
import com.dobudobu.newsapiapp.Dto.SignInRequest;
import com.dobudobu.newsapiapp.Dto.SignUpRequest;
import com.dobudobu.newsapiapp.Entity.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
