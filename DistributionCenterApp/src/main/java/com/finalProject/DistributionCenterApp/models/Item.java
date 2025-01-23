package com.finalProject.DistributionCenterApp.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {

    public enum Brand {
        PATAGONIA, NORTH_FACE, GUCCI, PRADA, LEVIS, ROOTS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Brand brand;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "distribution_center_id")
    private DistributionCenter distributionCenter;

    public void setDistributionCenter(DistributionCenter distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    @Builder
    public Item(Long id, String name, Brand brand, int quantity, DistributionCenter distributionCenter) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.distributionCenter = distributionCenter;
    }
}
