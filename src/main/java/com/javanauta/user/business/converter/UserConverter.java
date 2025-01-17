package com.javanauta.user.business.converter;

import com.javanauta.user.business.dto.UserDTO;
import com.javanauta.user.infrastructure.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final AddressConverter addressConverter;
    private final PhoneNumberConverter phoneNumberConverter;

    @Autowired
    public UserConverter(AddressConverter addressConverter, PhoneNumberConverter phoneNumberConverter) {
        this.addressConverter = addressConverter;
        this.phoneNumberConverter = phoneNumberConverter;
    }

    /**
     * Convert: UserDTO to User
     **/

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddresses() != null ?
                        addressConverter.toListAddress(userDTO.getAddresses()) : null)
                .phoneNumber(userDTO.getPhoneNumbers() != null ?
                        phoneNumberConverter.toListPhoneNumber(userDTO.getPhoneNumbers()) : null)
                .build();
    }


    /**
     * Convert: User to UserDTO
     * Alt+J
     **/

    public UserDTO toUserDTO(User userEntity) {
        return UserDTO.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .addresses(userEntity.getAddress() != null ?
                        addressConverter.toListAddressDTO(userEntity.getAddress()) : null)
                .phoneNumbers(userEntity.getPhoneNumber() != null ?
                        phoneNumberConverter.toListPhoneNumberDTO(userEntity.getPhoneNumber()) : null)
                .build();
    }

    // Method builder to compare DTO and Entity
    // Update only for User
    public User updateUser(UserDTO userDTO, User userEntity){
        return User.builder()
                // check if the userDTO give the name if it is different of null get the name DTO
                // if not get userEntity the one in the database
                .name(userDTO.getName() != null ? userDTO.getName() : userEntity.getName())
                // ID doesn't change so get from entity(database)
                .id(userEntity.getId())
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : userEntity.getEmail())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : userEntity.getPassword())
                // Address and PhoneNumber will be modified in other method
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }

}
