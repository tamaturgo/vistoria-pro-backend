package com.tamaturgo.provistoria.controllers;

import com.tamaturgo.provistoria.dto.checklist.ChecklistResponse;
import com.tamaturgo.provistoria.dto.checklist.CreateChecklistRequest;
import com.tamaturgo.provistoria.services.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @PostMapping
    public ResponseEntity<ChecklistResponse> createChecklist(
            @RequestBody CreateChecklistRequest request,
            @RequestHeader ("Authorization") String token
    ) {
        Thread.currentThread().setName("ChecklistController.createChecklist " + System.currentTimeMillis());
        ChecklistResponse response = checklistService.createChecklist(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}