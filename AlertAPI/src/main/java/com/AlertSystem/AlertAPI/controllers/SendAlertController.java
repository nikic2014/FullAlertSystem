package com.AlertSystem.AlertAPI.controllers;


import com.AlertSystem.AlertAPI.alerUtil.Alerter;
import com.AlertSystem.AlertAPI.dto.AlertDTO;
import com.AlertSystem.AlertAPI.util.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/alert")
public class SendAlertController {
    private final Alerter alerter;
    private final MyLogger myLogger;

    public SendAlertController(Alerter alerter, MyLogger myLogger) {
        this.alerter = alerter;
        this.myLogger = myLogger;
    }

    @PostMapping("/byRole/{role}")
    public ResponseEntity byRole(@PathVariable String role,
                                 @RequestBody AlertDTO alertDTO) {
        myLogger.sendInfo("Поступил запрос на оповещение по роли в проекте: " + alertDTO.getIdProject());
        try {
            alerter.sendAlert(alertDTO.getIdProject(),
                    alertDTO.getAlertType(),
                    alertDTO.getMessage(),
                    alertDTO.getAlertPath(),
                    role);
            return ResponseEntity.badRequest().body(Map.of("status", "success"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(Map.of("status", "Bad request"));
        }
    }

    @PostMapping("/byId/{id}")
    public ResponseEntity byId(@PathVariable int id,
                                 @RequestBody AlertDTO alertDTO) {
        myLogger.sendInfo("Поступил запрос на оповещение по id в проекте: " + alertDTO.getIdProject());
        try {
            alerter.sendAlert(alertDTO.getIdProject(),
                    alertDTO.getAlertType(),
                    alertDTO.getMessage(),
                    alertDTO.getAlertPath(),
                    id);
            return ResponseEntity.ok(Map.of("status", "success"));
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(Map.of("status", "Bad request"));
        }
    }
}
