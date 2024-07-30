package com.dobudobu.newsapiapp.Dto.Request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "invalid name format")
    private String fullname;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format")
    private String email;

    @NotBlank(message = "invalid password")
    private String password;

}
