package com.hammi.q_bus_backend.supabase.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_roles", schema = "public")
public class Roles {

    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sababtoo ah SMALLSERIAL waa auto-increment
    private Short roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
}
