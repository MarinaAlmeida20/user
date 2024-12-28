package com.javanauta.user.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String nameDTO;
    private String emailDTO;
    private String passwordDTO;
    private List<AddressDTO> addressesDTO;
    private List<PhoneNumberDTO> phoneNumbersDTO;

}
