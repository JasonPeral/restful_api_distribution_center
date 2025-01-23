package com.finalProject.DistributionCenterApp.repository;

import com.finalProject.DistributionCenterApp.models.DistributionCenter;
import com.finalProject.DistributionCenterApp.models.Item;
import com.finalProject.DistributionCenterApp.models.Item.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBrandAndName(Brand brand, String name);

    List<Item> findByBrandAndNameAndDistributionCenter(Brand brand, String name, DistributionCenter distributionCenter);




}
