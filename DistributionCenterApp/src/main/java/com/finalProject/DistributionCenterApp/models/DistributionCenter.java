package com.finalProject.DistributionCenterApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCenter {


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double latitude;
    private double longitude;
    @OneToMany(mappedBy = "distributionCenter")
    @JsonIgnore
    private List<Item> items;







    public String getItemsAvailable() {
        if (items != null && !items.isEmpty()) {
            return items.stream()
                    .map(Item::getName)
                    .collect(Collectors.joining(", "));
        }
        return "No items available";
    }



}
