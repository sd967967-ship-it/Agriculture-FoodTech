package com.example.controller;

import com.example.dto.KisanMitraChatRequest;
import com.example.dto.KisanMitraChatResponse;
import com.example.service.KisanMitraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class KisanMitraController {

    private final KisanMitraService kisanMitraService;

    public KisanMitraController(KisanMitraService kisanMitraService) {
        this.kisanMitraService = kisanMitraService;
    }

    @PostMapping("/kisanmitra/chat")
    public ResponseEntity<KisanMitraChatResponse> ask(@RequestBody KisanMitraChatRequest request) {
        if (request == null || request.question() == null || request.question().isBlank()) {
            return ResponseEntity.badRequest().body(new KisanMitraChatResponse(
                    "Please ask a farming question so I can help with crop care, disease, weather, or field decisions.",
                    "I can help with safe, local, evidence-based guidance; please avoid giving treatment details that are not verified by local agronomy advice.",
                    request != null && request.language() != null ? request.language() : "en",
                    false,
                    "validation"
            ));
        }

        return ResponseEntity.ok(kisanMitraService.ask(request));
    }
}
