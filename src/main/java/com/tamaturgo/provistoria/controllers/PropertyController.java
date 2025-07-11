package com.tamaturgo.provistoria.controllers;

import com.tamaturgo.provistoria.dto.property.PropertyRequest;
import com.tamaturgo.provistoria.dto.property.PropertyResponse;
import com.tamaturgo.provistoria.services.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody PropertyRequest request,
                                                           @RequestHeader("Authorization") String authorization) {
        Thread.currentThread().setName("create-property-thread " + System.currentTimeMillis());
        PropertyResponse response = propertyService.createProperty(request, authorization);
        return ResponseEntity.status(201).body(response);
    }
}
