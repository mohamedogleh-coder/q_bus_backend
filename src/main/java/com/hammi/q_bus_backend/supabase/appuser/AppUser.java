package com.hammi.q_bus_backend.supabase.appuser;


 import com.hammi.q_bus_backend.supabase.roles.Roles;
 import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 import java.util.Set;
 import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "app_users", schema = "public")
public class AppUser {
    @Id
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "auth_id")
    private UUID authId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_url")
    private String profileUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "app_user_roles",
            schema = "public",
            joinColumns = @JoinColumn(name = "user_phone"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;
}

