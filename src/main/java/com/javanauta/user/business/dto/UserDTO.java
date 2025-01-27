package com.javanauta.user.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private List<AddressDTO> addresses;
    private List<PhoneNumberDTO> phoneNumbers;

}
