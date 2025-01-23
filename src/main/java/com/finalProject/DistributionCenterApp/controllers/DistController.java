package com.finalProject.DistributionCenterApp.controllers;

import com.finalProject.DistributionCenterApp.models.DistributionCenter;
import com.finalProject.DistributionCenterApp.models.Item;
import com.finalProject.DistributionCenterApp.models.Item.Brand;
import com.finalProject.DistributionCenterApp.repository.DistributionCenterRepository;
import com.finalProject.DistributionCenterApp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/distributionCenters")
public class DistController {

    @Autowired
    private DistributionCenterRepository distributionCenterRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/{id}/items")
    public ResponseEntity<String> addItemToCenter(
            @PathVariable Long id,
            @RequestBody Item item
    ) {
        DistributionCenter center = distributionCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Distribution Center not found with id: " + id));

        item.setDistributionCenter(center);
        itemRepository.save(item);

        return ResponseEntity.ok("Item added to distribution center.");
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<String> deleteItemFromCenter(
            @PathVariable Long id,
            @PathVariable Long itemId
    ) {
        DistributionCenter center = distributionCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Distribution Center not found with id: " + id));

        Item itemToDelete = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + itemId));

        if (itemToDelete.getDistributionCenter() != null && itemToDelete.getDistributionCenter().equals(center)) {
            center.getItems().remove(itemToDelete);
            itemToDelete.setDistributionCenter(null);

            distributionCenterRepository.save(center);
            itemRepository.save(itemToDelete);

            return ResponseEntity.ok("Item deleted from distribution center.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item is not associated with the specified distribution center.");
        }
    }


    @GetMapping
    public List<DistributionCenter> getAllCenters() {
        return distributionCenterRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistributionCenter> getCenterById(@PathVariable Long id) {
        DistributionCenter center = distributionCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Distribution Center not found with id: " + id));

        return ResponseEntity.ok(center);
    }

    @GetMapping("/items")
    public List<Item> getItemsByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name
    ) {
        Brand brandEnum = Brand.valueOf(brand); // Converting String to Brand enum Maybe its easier to use later like this
        List<Item> items = itemRepository.findByBrandAndName(brandEnum, name);
        return items;
    }



}

