package com.javanauta.user.business.converter;

import com.javanauta.user.business.dto.PhoneNumberDTO;
import com.javanauta.user.infrastructure.entity.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneNumberConverter {

    /**
     * Convert: PhoneNumberDTO list to toListPhoneNumber entity list
     **/

    public List<PhoneNumber> toListPhoneNumber(List<PhoneNumberDTO> phoneNumberDTOList){
        return phoneNumberDTOList.stream().map(this::toPhoneNumber).toList();


        // 2 To get the list of addresses
//        List<PhoneNumber> phoneNumberList = new ArrayList<>();
//        for(PhoneNumberDTO phoneNumberDTO : phoneNumberDTOList){
//            phoneNumberList.add(toPhoneNumber(phoneNumberDTO));
//        }
//        return phoneNumberList;
    }

    /**
     * Convert: one PhoneNumberDTO to toPhoneNumber entity
     **/
    public PhoneNumber toPhoneNumber(PhoneNumberDTO phoneNumberDTO){
        return PhoneNumber.builder()
                .number(phoneNumberDTO.getNumber())
                .build();
    }

    /**
     * Convert: PhoneNumber list to toListPhoneNumberDTO dto
     **/
    public List<PhoneNumberDTO> toListPhoneNumberDTO(List<PhoneNumber> phoneNumberList){
        return phoneNumberList.stream().map(this::toPhoneNumberDTO).toList();
    }

    /**
     * Convert: One PhoneNumber to toPhoneNumberDTO dto
     **/
    public PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumber){
        return PhoneNumberDTO.builder()
                .id(phoneNumber.getId())
                .number(phoneNumber.getNumber())
                .build();
    }

    /**
     * Update: PhoneNumberDTO update the database PhoneNumber entity
     **/
    public PhoneNumber updatePhoneNumber(PhoneNumberDTO phoneNumberDTO, PhoneNumber phoneNumberEntity){
        return PhoneNumber.builder()
                .id(phoneNumberEntity.getId())
                .number(phoneNumberDTO.getNumber() != null ? phoneNumberDTO.getNumber() : phoneNumberEntity.getNumber())
                .build();
    }

    /**
     * Add new PhoneNumber to db: the toPhoneNumberEntity will receive 2 params
     * PhoneNumberDTO adn (User id)idUser
     **/
    public PhoneNumber toPhoneNumberEntity(PhoneNumberDTO phoneNumberDTO, Long idUser){
        return PhoneNumber.builder()
                .number(phoneNumberDTO.getNumber())
                .user_id(idUser)
                .build();
    }
}
