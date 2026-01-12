package com.mayb.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mayb.api.entity.User;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}