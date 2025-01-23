package com.finalProject.DistributionCenterApp.repository;

import com.finalProject.DistributionCenterApp.models.Item;
import com.finalProject.DistributionCenterApp.models.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
    WarehouseStock findByItem(Item item);
}

