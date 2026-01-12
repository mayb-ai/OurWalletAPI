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
        return familyRepository.save(family);
    }

    public List<Family> findAllFamilies() {
        return familyRepository.findAll();
    }
}
