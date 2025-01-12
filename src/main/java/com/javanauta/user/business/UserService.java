package com.javanauta.user.business;

import com.javanauta.user.business.converter.UserConverter;
import com.javanauta.user.business.dto.UserDTO;
import com.javanauta.user.infrastructure.entity.User;
import com.javanauta.user.infrastructure.exceptions.ConflictException;
import com.javanauta.user.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.user.infrastructure.repository.UserRepository;
import com.javanauta.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * line 22: Receive an userDTO
     * line 30: Transform it in an entity user
     * line 31: save it in database, this database return a user entity
     * line 32: transform user entity to userDTO
     **/
    public UserDTO saveUser(UserDTO userDTO){
//        try{
//            emailExists(userDTO.getEmailDTO());
//            userDTO.setPasswordDTO(passwordEncoder.encode(userDTO.getPasswordDTO()));
//
//            User user = userConverter.toUser(userDTO);
//            user = userRepository.save(user);
//            return userConverter.toUserDTO(user);
//        } catch (ConflictException e) {
//            throw new ConflictException("Email already registered " + e.getCause());
//        }
        emailExists(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userConverter.toUser(userDTO);
        return userConverter.toUserDTO(
                userRepository.save(user)
        );


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

    public UserDTO findByEmail(String email){

        try {
            return userConverter.toUserDTO(
                    userRepository.findByEmail(email)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Email not found " + email)
                            )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email not found " + email);
        }

//        return userRepository.findByEmail(email).orElseThrow(
//                () -> new ResourceNotFoundException("Email not found " + email));
    }

//    public List<User> findAll(){
//        return userRepository.findAll();
//    }

    public void deleteUserByEmail(String email){
        userRepository.deleteByEmail(email);
    }

    public UserDTO updateUserData(String token, UserDTO userDTO){
        // Get email from token
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // encrypted password
        userDTO.setPassword(
                userDTO.getPassword() != null ?
                        passwordEncoder.encode(userDTO.getPassword()) : null);

        //Get user data from database
        User userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email Not Found"));

        // Compare User Entity with UserDTO
        User user = userConverter.updateUser(userDTO, userEntity);

        // Saved the data from user converted and got the return and converted to UserDTO
        return userConverter.toUserDTO(userRepository.save(user));

    }
}
