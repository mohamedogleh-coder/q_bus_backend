package com.hammi.q_bus_backend.supabase.appuser;

import com.hammi.q_bus_backend.exceptions.NotFoundException;
import com.hammi.q_bus_backend.supabase.roles.RoleRepository;
import com.hammi.q_bus_backend.supabase.roles.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository rolesRepository;

    public AppUserDTO findByPhoneNumber(String phoneNumber) {
         var user= appUserRepository.findDriverByPhoneNumber(phoneNumber).orElseThrow(() -> new NotFoundException("Driver not found"));
        return new AppUserDTO(user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), null,null);
    }


    public AppUserDTO registerNewDriver(AppUserDAO request) {
        var driverRole = rolesRepository.findByRoleName("DRIVER").orElseThrow(() -> new NotFoundException("Role not found"));
        var roles = Set.of(driverRole);
        var user = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .roles(roles)
                .build();

        appUserRepository.save(user);
        var roleNames = roles.stream().map(Roles::getRoleName).toList();
        return new AppUserDTO(user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), null, roleNames);
    }
}
