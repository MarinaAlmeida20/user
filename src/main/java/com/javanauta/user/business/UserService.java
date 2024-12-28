package com.javanauta.user.business;

import com.javanauta.user.business.converter.UserConverter;
import com.javanauta.user.business.dto.UserDTO;
import com.javanauta.user.infrastructure.entity.User;
import com.javanauta.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    /**
     * line 22: Receive an userDTO
     * line 23: Transform it in an entity user
     * line 24: save it in database, this database return a user entity
     * line 26: transform user entity to userDTO
     **/
    public UserDTO saveUser(UserDTO userDTO){
        User user = userConverter.toUser(userDTO);
        user = userRepository.save(user);
        return userConverter.toUserDTO(user);
    }
}
