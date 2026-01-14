package com.mayb.api.controller;

import com.mayb.api.entity.Family;
import com.mayb.api.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/families")
public class FamilyController {

    @Autowired
    private FamilyService familyService; // <--- Agora chamamos o Service, não o Repository

    @PostMapping
    public Family createFamily(@RequestBody Family family) {
        return familyService.createFamily(family); // Passa a bola para o Service
    }

    @GetMapping
    public List<Family> getAllFamilies() {
        return familyService.findAllFamilies();
    }

    //Buscar pelo código (Ex: /families/invite/FAM-1234)
    @GetMapping("/invite/{code}")
    public Family getFamilyByCode(@PathVariable String code) {
        return familyService.findByInviteCode(code);
    }
}