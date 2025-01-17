package com.javanauta.user.controller;

import com.javanauta.user.business.UserService;
import com.javanauta.user.business.dto.AddressDTO;
import com.javanauta.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AddressController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> addToAddress(@RequestBody AddressDTO addressDTO,
                                                   @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.addToAddress(token, addressDTO));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO,
                                                    @RequestParam("id") Long id){
        return ResponseEntity.ok(userService.updateAddress(id, addressDTO));
    }
}
