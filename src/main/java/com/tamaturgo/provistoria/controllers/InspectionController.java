package com.tamaturgo.provistoria.controllers;

import com.tamaturgo.provistoria.dto.inspection.CreateInspectionRequest;
import com.tamaturgo.provistoria.dto.inspection.InspectionDetailsResponse;
import com.tamaturgo.provistoria.services.InspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final InspectionService inspectionService;

    @PostMapping
    public ResponseEntity<Void> createInspection(
            @RequestBody CreateInspectionRequest request,
            @RequestHeader("Authorization") String token
    ) {
        Thread.currentThread().setName("InspectionController.createInspection " + System.currentTimeMillis());
        inspectionService.createInspection(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<InspectionDetailsResponse>> listInspections(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(inspectionService.listInspections(token, page, size));
    }
}