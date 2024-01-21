package com.AlertSystem.AlertAPI.controllers;


import com.AlertSystem.AlertAPI.alerUtil.Alerter;
import com.AlertSystem.AlertAPI.dto.AlertDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/alert")
public class SendAlertController {
    private final Alerter alerter;

    public SendAlertController(Alerter alerter) {
        this.alerter = alerter;
    }

    @PostMapping("/byRole/{role}")
    public ResponseEntity byRole(@PathVariable String role,
                                 @RequestBody AlertDTO testDTO) {
        try {
            alerter.sendAlert(testDTO.getIdProject(),
                    testDTO.getAlertType(),
                    testDTO.getMessage(),
                    testDTO.getAlertPath(),
                    role);
            return ResponseEntity.badRequest().body(Map.of("status", "success"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(Map.of("status", "Bad request"));
        }
    }

    @PostMapping("/byId/{id}")
    public ResponseEntity byId(@PathVariable int id,
                                 @RequestBody AlertDTO testDTO) {
        try {
            alerter.sendAlert(testDTO.getIdProject(),
                    testDTO.getAlertType(),
                    testDTO.getMessage(),
                    testDTO.getAlertPath(),
                    id);
            return ResponseEntity.ok(Map.of("status", "success"));
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(Map.of("status", "Bad request"));
        }
    }
}
