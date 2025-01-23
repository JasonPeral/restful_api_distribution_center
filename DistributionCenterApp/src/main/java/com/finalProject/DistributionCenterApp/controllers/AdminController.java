package com.finalProject.DistributionCenterApp.controllers;

import com.finalProject.DistributionCenterApp.models.DistributionCenter;
import com.finalProject.DistributionCenterApp.models.Item;
import com.finalProject.DistributionCenterApp.models.Item.Brand;
import com.finalProject.DistributionCenterApp.models.WarehouseStock;
import com.finalProject.DistributionCenterApp.repository.DistributionCenterRepository;
import com.finalProject.DistributionCenterApp.repository.ItemRepository;
import com.finalProject.DistributionCenterApp.repository.WarehouseStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DistributionCenterRepository distributionCenterRepository;

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;




    @GetMapping("/distribution")
    public String adminDistributionPage(Model model) {
        List<DistributionCenter> distributionCenters = distributionCenterRepository.findAll();
        model.addAttribute("distributionCenters", distributionCenters);

        double warehouseLat = 43.772094;
        double warehouseLon = -79.400279;
        model.addAttribute("warehouseLat", warehouseLat);
        model.addAttribute("warehouseLon", warehouseLon);

        //all items from the ItemRepository
        List<Item> allItems = itemRepository.findAll();
        model.addAttribute("allItems", allItems);

        //all items from the WarehouseStockRepository
        List<WarehouseStock> warehouseItems = warehouseStockRepository.findAll();
        model.addAttribute("warehouseItems", warehouseItems);

        return "admin/distribution";
    }


    @PostMapping("/requestItem")
    public String requestItem(@RequestParam String brand, @RequestParam String name, Model model) {
        List<Item> requestedItems = requestItemsFromClosestCenter(brand, name);

        if (!requestedItems.isEmpty()) {
            model.addAttribute("requestedItems", requestedItems);
            return "admin/requested_items";
        } else {
            model.addAttribute("errorMessage", "Requested item not available");
            return "error";
        }
    }




    @PostMapping("/replenishStock")
    public String replenishStock(@RequestParam Long itemId, @RequestParam int quantity, Model model) {
        Item requestedItem = itemRepository.findById(itemId).orElse(null);

        if (requestedItem != null) {
            WarehouseStock warehouseStock = warehouseStockRepository.findByItem(requestedItem);

            if (warehouseStock == null) {
                // Create a new WarehouseStock entry for the requested item
                warehouseStock = new WarehouseStock();
                warehouseStock.setItem(requestedItem);
            }

            //Add stock to warehouse stock
            warehouseStock.setStock(warehouseStock.getStock() + quantity);
            warehouseStockRepository.save(warehouseStock);

            // Deducting the distribution item from the required distribution center
            int newQuantity = requestedItem.getQuantity() - quantity;
            if (newQuantity >= 0) {
                requestedItem.setQuantity(newQuantity);
                itemRepository.save(requestedItem);
            } else {
                model.addAttribute("errorMessage", "Stock can't be replenished");
                return "error";
            }

            return "redirect:/admin/distribution"; // Redirect to distribution page
        } else {
            model.addAttribute("errorMessage", "Requested item not found");
            return "error";
        }
    }


    private List<Item> requestItemsFromClosestCenter(String brandStr, String name) {
        List<Item> requestedItems = new ArrayList<>();
        //Centers with available items
        List<DistributionCenter> centersWithAvailableItems = distributionCenterRepository.findCentersWithAvailableItems();
        //sorting of distribution centers
        centersWithAvailableItems.sort(Comparator.comparingDouble(center ->
                calculateDistance(center.getLatitude(), center.getLongitude(), 43.772094, -79.400279)));
        // Find the closest distribution center
        DistributionCenter closestCenter = centersWithAvailableItems.get(0);

        // Then getting items that match the brand and name that I queried
        List<Item> matchingItems = itemRepository.findByBrandAndName(Brand.valueOf(brandStr), name);

        // Filter items based on distribution centers
        for (Item item : matchingItems) {
            DistributionCenter itemCenter = item.getDistributionCenter();
            if (itemCenter != null && (itemCenter.equals(closestCenter) || itemCenter.getItems().size() == 1)) {
                requestedItems.add(item);
            }
        }

        return requestedItems;
    }





    // Method to calculate distance between two points using Haversine formula
    //https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of the earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}