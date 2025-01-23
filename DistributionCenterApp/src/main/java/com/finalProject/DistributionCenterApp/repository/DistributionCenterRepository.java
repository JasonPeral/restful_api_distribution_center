package com.finalProject.DistributionCenterApp.repository;

import com.finalProject.DistributionCenterApp.models.DistributionCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionCenterRepository extends JpaRepository<DistributionCenter, Long> {
    @Query("SELECT dc FROM DistributionCenter dc JOIN FETCH dc.items")
    List<DistributionCenter> findCentersWithAvailableItems();

}
