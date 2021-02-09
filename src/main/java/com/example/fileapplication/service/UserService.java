package com.example.fileapplication.service;

import com.example.fileapplication.dto.UserDto;
import com.example.fileapplication.util.TPage;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto save(UserDto user);

    UserDto getById(Long id);

    TPage<UserDto> getAllPageable(Pageable pageable);

    UserDto getByUsername(String username);
}
