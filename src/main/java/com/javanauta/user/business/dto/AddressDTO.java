package com.javanauta.user.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private Long id;
    private String road;
    private String number;
    private String city;
    private String postcode;
}
