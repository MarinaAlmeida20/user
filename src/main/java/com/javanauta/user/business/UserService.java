package com.javanauta.user.business;

import com.javanauta.user.business.converter.UserConverter;
import com.javanauta.user.business.dto.UserDTO;
import com.javanauta.user.infrastructure.entity.User;
import com.javanauta.user.infrastructure.exceptions.ConflictException;
import com.javanauta.user.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    /**
     * line 22: Receive an userDTO
     * line 30: Transform it in an entity user
     * line 31: save it in database, this database return a user entity
     * line 32: transform user entity to userDTO
     **/
    public UserDTO saveUser(UserDTO userDTO){
        try{
            emailExists(userDTO.getEmailDTO());
            userDTO.setPasswordDTO(passwordEncoder.encode(userDTO.getPasswordDTO()));

            User user = userConverter.toUser(userDTO);
            user = userRepository.save(user);
            return userConverter.toUserDTO(user);
        } catch (ConflictException e) {
            throw new ConflictException("Email already registered " + e.getCause());
        }


    }

    public void emailExists(String email){
        try{
            boolean exist = verifyEmailExists(email);

            if (exist){
                throw new ConflictException("Email already exists " + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email already exists ", e.getCause());
        }
    }

    public boolean verifyEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found " + email));
    }

    public void deleteUserByEmail(String email){
        userRepository.deleteByEmail(email);
    }
}
