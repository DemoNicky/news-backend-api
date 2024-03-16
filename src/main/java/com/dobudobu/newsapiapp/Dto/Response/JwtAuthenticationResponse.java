package com.dobudobu.newsapiapp.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;

}
