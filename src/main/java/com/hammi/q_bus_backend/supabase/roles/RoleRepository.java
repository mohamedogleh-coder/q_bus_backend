package com.hammi.q_bus_backend.supabase.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Short> {
    Optional<Roles> findByRoleName(String roleName);
}
