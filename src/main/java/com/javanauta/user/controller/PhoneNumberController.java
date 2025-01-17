package com.javanauta.user.controller;

import com.javanauta.user.business.UserService;
import com.javanauta.user.business.dto.PhoneNumberDTO;
import com.javanauta.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/phoneNumber")
    public ResponseEntity<PhoneNumberDTO> addToPhoneNumber(@RequestBody PhoneNumberDTO phoneNumberDTO,
                                                           @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.addToPhoneNumber(token, phoneNumberDTO));
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<PhoneNumberDTO> updatePhoneNumber(@RequestBody PhoneNumberDTO phoneNumberDTO,
                                                            @RequestParam("id") Long id){
        return ResponseEntity.ok(userService.updatePhoneNumber(id, phoneNumberDTO));
    }
}
