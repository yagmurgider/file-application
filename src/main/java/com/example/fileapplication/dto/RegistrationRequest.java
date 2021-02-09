package com.example.fileapplication.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;

}
