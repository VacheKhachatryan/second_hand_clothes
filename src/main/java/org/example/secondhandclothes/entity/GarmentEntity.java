package org.example.secondhandclothes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "garments")
@NoArgsConstructor
@AllArgsConstructor
@With
@EqualsAndHashCode
@Data
public class GarmentEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private GarmentCategoryEntity category;

    private BigDecimal price;

    private String size;

    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
