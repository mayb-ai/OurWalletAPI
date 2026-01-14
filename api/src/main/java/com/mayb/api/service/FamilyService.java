package com.mayb.api.service;

import com.mayb.api.entity.Family;
import com.mayb.api.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FamilyService {
    @Autowired
    private FamilyRepository familyRepository;

    public Family createFamily(Family family) {
        // Regra 1: Família precisa de nome
        if (family.getName() == null || family.getName().trim().isEmpty()) {
            throw new RuntimeException("A família precisa ter um nome (Sobrenome).");
        }
        return familyRepository.save(family);
    }

    public List<Family> findAllFamilies() {
        return familyRepository.findAll();
    }

    // Regra 2: Buscar família pelo código (Para entrar nela)
    public Family findByInviteCode(String code) {
        // Tenta achar. Se não achar, explode um erro amigável.
        return familyRepository.findByInviteCode(code)
                .orElseThrow(() -> new RuntimeException("Família não encontrada com este código: " + code));
    }
}
