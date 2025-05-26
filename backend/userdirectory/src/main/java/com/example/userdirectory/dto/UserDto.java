package com.example.userdirectory.dto;

import lombok.Data;

public record UserDto(Long id, String name, String email, String role) {

}
