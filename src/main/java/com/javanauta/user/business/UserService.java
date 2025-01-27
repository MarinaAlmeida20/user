package com.javanauta.user.business;

import com.javanauta.user.business.converter.AddressConverter;
import com.javanauta.user.business.converter.PhoneNumberConverter;
import com.javanauta.user.business.converter.UserConverter;
import com.javanauta.user.business.dto.AddressDTO;
import com.javanauta.user.business.dto.PhoneNumberDTO;
import com.javanauta.user.business.dto.UserDTO;
import com.javanauta.user.infrastructure.entity.Address;
import com.javanauta.user.infrastructure.entity.PhoneNumber;
import com.javanauta.user.infrastructure.entity.User;
import com.javanauta.user.infrastructure.exceptions.ConflictException;
import com.javanauta.user.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.user.infrastructure.repository.AddressRepository;
import com.javanauta.user.infrastructure.repository.PhoneNumberRepository;
import com.javanauta.user.infrastructure.repository.UserRepository;
import com.javanauta.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    private final UserConverter userConverter;
    private final AddressConverter addressConverter;
    private final PhoneNumberConverter phoneNumberConverter;

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

    public UserDTO findUserByEmail(String email){

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

    public AddressDTO updateAddress (Long idAddress, AddressDTO addressDTO){
        Address addressEntity = addressRepository.findById(idAddress).orElseThrow(
                () -> new ResourceNotFoundException("Not Found ID " + idAddress)
        );

        Address address = addressConverter.updateAddress(addressDTO, addressEntity);

        return addressConverter.toAddressDTO(addressRepository.save(address));
    }

    public PhoneNumberDTO updatePhoneNumber (Long idPhone, PhoneNumberDTO phoneDTO){
        PhoneNumber phoneNumberEntity = phoneNumberRepository.findById(idPhone).orElseThrow(
                () -> new ResourceNotFoundException("Not Found ID " + idPhone)
        );

        PhoneNumber phoneNumber = phoneNumberConverter.updatePhoneNumber(phoneDTO, phoneNumberEntity);

        return phoneNumberConverter.toPhoneNumberDTO(phoneNumberRepository.save(phoneNumber));
    }

    // Add new address ->  receive 2 params token(to be unique) and addressDTO(the new address to be added)
    public AddressDTO addToAddress(String token,AddressDTO addressDTO){
        // Get the token and extract the email from the User that will be added a new address
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // find the user using the repository (which is way to get information from db)
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email Not Found" + email));

        // convert from dto to entity using user id as reference
        Address address = addressConverter.toAddressEntity(addressDTO, user.getId());

        // save the converted dto into the db
        Address addressEntity = addressRepository.save(address);

        // get from the db the address saved and convert back to dto with the new address in the user
        return addressConverter.toAddressDTO(addressEntity);

    }

    // Add new address -.  receive 2 params token(to be unique) and addressDTO(the new address to be added)
    public PhoneNumberDTO addToPhoneNumber(String token,PhoneNumberDTO phoneNumberDTO){
        // Get the token and extract the email from the User that will be added a new address
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // find the user using the repository (which is way to get information from db)
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email Not Found" + email));

        // convert from dto to entity using user id as reference
        PhoneNumber phoneNumber = phoneNumberConverter.toPhoneNumberEntity(phoneNumberDTO, user.getId());

        // save the converted dto into the db
        PhoneNumber phoneNumberEntity = phoneNumberRepository.save(phoneNumber);

        // get from the db the phone number saved and convert back to dto with the new phoneNumberDto in the user
        return phoneNumberConverter.toPhoneNumberDTO(phoneNumberEntity);

    }


}
