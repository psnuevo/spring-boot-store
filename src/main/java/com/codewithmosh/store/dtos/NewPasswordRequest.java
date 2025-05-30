package com.codewithmosh.store.dtos;

import lombok.Data;

@Data
public class NewPasswordRequest {

    private String oldPassword;
    private String newPassword;
}
