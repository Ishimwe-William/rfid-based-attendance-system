package com.bunsen.rfidbasedattendancesystem.service;



import com.bunsen.rfidbasedattendancesystem.model.User;
import com.bunsen.rfidbasedattendancesystem.model.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
