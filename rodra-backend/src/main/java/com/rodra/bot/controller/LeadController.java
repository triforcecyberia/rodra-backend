package com.rodra.bot.controller;

import com.rodra.bot.dto.LeadRequest;
import com.rodra.bot.service.LeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @PostMapping("/lead")
    public ResponseEntity<Map<String, String>> submitLead(@RequestBody LeadRequest request) {
        try {
            leadService.processLead(request);
            return ResponseEntity.ok(Map.of(
                    "status", "ok",
                    "message", "Заявка успешно отправлена"
            ));
        } catch (Exception e) {
            log.error("Ошибка обработки заявки: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Не удалось отправить заявку"
            ));
        }
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
