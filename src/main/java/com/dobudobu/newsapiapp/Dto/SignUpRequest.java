package com.dobudobu.newsapiapp.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
public class SignUpRequest {

    private String fullname;

    private String phoneNumber;

    private String email;

    private String password;

}
