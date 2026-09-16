package com.example.controller;

import com.example.entity.Farm;
import com.example.repository.FarmRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FarmController {

    private final FarmRepository farmRepository;

    public FarmController(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @GetMapping("/farms")
    public List<Map<String, Object>> getFarms(@RequestParam(value = "farmerUsername", required = false) String farmerUsername) {
        List<Farm> farms = (farmerUsername == null || farmerUsername.isBlank())
                ? farmRepository.findAll()
                : farmRepository.findByFarmerUsernameOrderByIdDesc(farmerUsername);
        return farms.stream().map(this::toMap).toList();
    }

    @PostMapping("/farms")
    public ResponseEntity<Map<String, Object>> createFarm(@RequestBody Map<String, String> payload) {
        String name = payload.getOrDefault("name", "").trim();
        String farmerUsername = payload.getOrDefault("farmerUsername", "").trim();
        String district = payload.getOrDefault("district", "").trim();

        if (name.isBlank() || farmerUsername.isBlank() || district.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "VALIDATION_ERROR", "message", "Farm name, farmer username and district are required."));
        }

        Farm farm = farmRepository.save(new Farm(name, farmerUsername, district));
        return ResponseEntity.ok(toMap(farm));
    }

    private Map<String, Object> toMap(Farm farm) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", farm.getId());
        map.put("name", farm.getName());
        map.put("farmerUsername", farm.getFarmerUsername());
        map.put("district", farm.getDistrict());
        map.put("fieldCount", farm.getFields().size());
        return map;
    }
}
