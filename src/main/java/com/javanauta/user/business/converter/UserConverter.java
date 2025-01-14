package com.javanauta.user.business.converter;

import com.javanauta.user.business.dto.AddressDTO;
import com.javanauta.user.business.dto.PhoneNumberDTO;
import com.javanauta.user.business.dto.UserDTO;
import com.javanauta.user.infrastructure.entity.Address;
import com.javanauta.user.infrastructure.entity.PhoneNumber;
import com.javanauta.user.infrastructure.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    /**
     * Convert: UserDTO to User
     **/

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddresses() != null ?
                        toListAddress(userDTO.getAddresses()) : null)
                .phoneNumber(userDTO.getPhoneNumbers() != null ?
                        toListPhoneNumber(userDTO.getPhoneNumbers()) : null)
                .build();
    }

    // Get the list of addresses
    public List<Address> toListAddress(List<AddressDTO> addressDTOList){

        // 1 way to get the list of addresses
        return addressDTOList.stream().map(this::toAddress).toList();

        // 2 To get the list of addresses
//            List<Address> addresses = new ArrayList<>();
//            for (AddressDTO addressDTO : addressDTOList){
//                addresses.add(toAddress(addressDTO));
//            }
//            return addresses;
    }

    // Get only one address
    public Address toAddress(AddressDTO addressDTO){
        return Address.builder()
                .city(addressDTO.getCity())
                .road(addressDTO.getRoad())
                .number(addressDTO.getNumber())
                .postcode(addressDTO.getPostcode())
                .build();
    }

    public List<PhoneNumber> toListPhoneNumber(List<PhoneNumberDTO> phoneNumberDTOList){
        return phoneNumberDTOList.stream().map(this::toPhoneNumber).toList();


        // 2 To get the list of addresses
//        List<PhoneNumber> phoneNumberList = new ArrayList<>();
//        for(PhoneNumberDTO phoneNumberDTO : phoneNumberDTOList){
//            phoneNumberList.add(toPhoneNumber(phoneNumberDTO));
//        }
//        return phoneNumberList;
    }

    public PhoneNumber toPhoneNumber(PhoneNumberDTO phoneNumberDTO){
        return PhoneNumber.builder()
                .number(phoneNumberDTO.getNumber())
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
                        toListAddressDTO(userEntity.getAddress()) : null)
                .phoneNumbers(userEntity.getPhoneNumber() != null ?
                        toListPhoneNumberDTO(userEntity.getPhoneNumber()) : null)
                .build();
    }

    // Get the list of addresses
    public List<AddressDTO> toListAddressDTO(List<Address> addressList){

        // 1 way to get the list of addresses
//        return addressList.stream().map(this::toAddressDTO).toList();

        // 2 To get the list of addresses
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(Address address : addressList){
            addressDTOList.add(toAddressDTO(address));
        }
        return addressDTOList;
    }

    // Get only one address
    public AddressDTO toAddressDTO(Address address){
        return AddressDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .road(address.getRoad())
                .number(address.getNumber())
                .postcode(address.getPostcode())
                .build();
    }

    public List<PhoneNumberDTO> toListPhoneNumberDTO(List<PhoneNumber> phoneNumberList){
        return phoneNumberList.stream().map(this::toPhoneNumberDTO).toList();
    }

    public PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumber){
        return PhoneNumberDTO.builder()
                .id(phoneNumber.getId())
                .number(phoneNumber.getNumber())
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

    public Address updateAddress(AddressDTO addressDTO, Address addressEntity){
        return Address.builder()
                .id(addressEntity.getId())
                .road(addressDTO.getRoad() != null ? addressDTO.getRoad() : addressEntity.getRoad())
                .postcode(addressDTO.getPostcode() != null ? addressDTO.getPostcode() : addressEntity.getPostcode())
                .number(addressDTO.getNumber() != null ? addressDTO.getNumber() : addressEntity.getNumber())
                .city(addressDTO.getCity() != null ? addressDTO.getCity() : addressEntity.getCity())
                .build();
    }

    public PhoneNumber updatePhoneNumber(PhoneNumberDTO phoneNumberDTO, PhoneNumber phoneNumberEntity){
        return PhoneNumber.builder()
                .id(phoneNumberEntity.getId())
                .number(phoneNumberDTO.getNumber() != null ? phoneNumberDTO.getNumber() : phoneNumberEntity.getNumber())
                .build();
    }
}
