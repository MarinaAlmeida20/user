package com.javanauta.user.business.converter;

import com.javanauta.user.business.dto.AddressDTO;
import com.javanauta.user.infrastructure.entity.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressConverter {

    /**
     * Convert: AddressDTO list to toListAddress entity list
     **/
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

    /**
     * Convert: One AddressDTO to toAddress entity
     **/
    // Get only one address
    public Address toAddress(AddressDTO addressDTO){
        return Address.builder()
                .city(addressDTO.getCity())
                .road(addressDTO.getRoad())
                .number(addressDTO.getNumber())
                .postcode(addressDTO.getPostcode())
                .build();
    }

    /**
     * Convert: One Address entity to toAddressDto dto
     **/
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

    /**
     * Convert: Address list to toListAddressDTO dto
     **/
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

    /**
     * Update: AddressDTO update the database Address entity
     **/
    public Address updateAddress(AddressDTO addressDTO, Address addressEntity){
        return Address.builder()
                .id(addressEntity.getId())
                .road(addressDTO.getRoad() != null ? addressDTO.getRoad() : addressEntity.getRoad())
                .postcode(addressDTO.getPostcode() != null ? addressDTO.getPostcode() : addressEntity.getPostcode())
                .number(addressDTO.getNumber() != null ? addressDTO.getNumber() : addressEntity.getNumber())
                .city(addressDTO.getCity() != null ? addressDTO.getCity() : addressEntity.getCity())
                .build();
    }
}
