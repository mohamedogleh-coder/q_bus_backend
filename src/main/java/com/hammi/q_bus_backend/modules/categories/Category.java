package com.hammi.q_bus_backend.modules.categories;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(name = "category_unq", columnNames = {"category_name", "model_year"})
        }
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "model_year", nullable = false, length = 10)
    private String modelYear;

    @Column(name = "number_of_seats", nullable = false)
    private Short numberOfSeats;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;
}