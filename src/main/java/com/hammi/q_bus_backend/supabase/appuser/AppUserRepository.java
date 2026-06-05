package com.hammi.q_bus_backend.supabase.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
     Optional<AppUser> findDriverByPhoneNumber(String phoneNumber);


     List<AppUser> findAllByPhoneNumberIn(List<String> phoneNumbers);
}