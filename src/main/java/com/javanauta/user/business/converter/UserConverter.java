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
                .name(userDTO.getNameDTO())
                .email(userDTO.getEmailDTO())
                .password(userDTO.getPasswordDTO())
                .addresses(userDTO.getAddressesDTO() != null ?
                        toListAddress(userDTO.getAddressesDTO()) : null)
                .phoneNumber(userDTO.getPhoneNumbersDTO() != null ?
                        toListPhoneNumber(userDTO.getPhoneNumbersDTO()) : null)
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

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .nameDTO(user.getName())
                .emailDTO(user.getEmail())
                .passwordDTO(user.getPassword())
                .addressesDTO(user.getAddresses() != null ?
                        toListAddressDTO(user.getAddresses()) : null)
                .phoneNumbersDTO(user.getPhoneNumber() != null ?
                        toListPhoneNumberDTO(user.getPhoneNumber()) : null)
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
}
