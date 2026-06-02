package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.modules.categories.Category;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(
        name = "buses",
        uniqueConstraints = {
                @UniqueConstraint(name = "plate_number_unq", columnNames = "palate_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Spring Boot ayaa dhalinaya UUID-ga
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "palate_number", length = 10, nullable = false)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "steering_side", length = 10, nullable = false)
    private SteeringSide steeringSide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}