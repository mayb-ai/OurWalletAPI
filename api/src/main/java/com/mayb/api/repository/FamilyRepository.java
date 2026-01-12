package com.mayb.api.repository;

import com.mayb.api.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID>{

}
