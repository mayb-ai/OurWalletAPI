package com.mayb.api.repository;

import com.mayb.api.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID>{
    // O Spring monta o SQL: "SELECT * FROM families WHERE invite_code = ?"
    Optional<Family> findByInviteCode(String inviteCode);
}
