package com.javanauta.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "road")
    private String road;
    @Column(name = "number")
    private String number;
    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "postcode", length = 6)
    private String postcode;

}
