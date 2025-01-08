package com.usp.mba.adapters.rest.dto;

import com.usp.mba.domain.enums.Technology;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String linkedInProfile;

    private String role;
    private int yearsOfExperience;
    private Technology primaryTechnology;
    private Technology secondaryTechnology;
}
