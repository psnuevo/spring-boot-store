package com.codewithmosh.store.auth;

import lombok.Data;

@Data
public class NewPasswordRequest {

    private String oldPassword;
    private String newPassword;
}
