package com.example.controller;

import com.example.entity.Hotspot;
import com.example.repository.HotspotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class HotspotController {
    private final HotspotRepository repository;

    public HotspotController(HotspotRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hotspots")
    public List<Map<String, Object>> getHotspots() {
        return repository.findAll().stream().map(this::toMap).toList();
    }

    @PostMapping("/hotspots")
    public ResponseEntity<Map<String, Object>> createHotspot(@RequestBody Map<String, Object> payload) {
        String district = String.valueOf(payload.getOrDefault("district", "")).trim();
        String village = String.valueOf(payload.getOrDefault("village", "")).trim();
        String riskLevel = String.valueOf(payload.getOrDefault("riskLevel", "MEDIUM")).trim();
        Object caseCount = payload.get("caseCount");

        if (district.isBlank() || village.isBlank() || caseCount == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "VALIDATION_ERROR", "message", "district, village and caseCount are required."));
        }

        Hotspot hotspot = repository.save(new Hotspot(district, village, riskLevel, Integer.parseInt(caseCount.toString())));
        return ResponseEntity.ok(toMap(hotspot));
    }

    private Map<String, Object> toMap(Hotspot hotspot) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", hotspot.getId());
        map.put("district", hotspot.getDistrict());
        map.put("village", hotspot.getVillage());
        map.put("riskLevel", hotspot.getRiskLevel());
        map.put("caseCount", hotspot.getCaseCount());
        map.put("createdAt", hotspot.getCreatedAt());
        return map;
    }
}
