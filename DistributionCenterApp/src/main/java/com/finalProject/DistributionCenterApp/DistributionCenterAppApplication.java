package com.finalProject.DistributionCenterApp;

import com.finalProject.DistributionCenterApp.models.DistributionCenter;
import com.finalProject.DistributionCenterApp.models.Item;
import com.finalProject.DistributionCenterApp.repository.DistributionCenterRepository;
import com.finalProject.DistributionCenterApp.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DistributionCenterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributionCenterAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(DistributionCenterRepository distributionCenterRepository, ItemRepository itemRepository) {
		return args -> {
			DistributionCenter center1 = DistributionCenter.builder()
					.name("Mississauga Center")
					.latitude(43.5890)
					.longitude(-79.6441)
					.build();
			distributionCenterRepository.save(center1);

			DistributionCenter center2 = DistributionCenter.builder()
					.name("Pickering Center")
					.latitude(43.8358)
					.longitude(-79.0905)
					.build();
			distributionCenterRepository.save(center2);

			DistributionCenter center3 = DistributionCenter.builder()
					.name("Waterloo Center")
					.latitude(43.4643)
					.longitude(-80.5204)
					.build();
			distributionCenterRepository.save(center3);

			DistributionCenter center4 = DistributionCenter.builder()
					.name("Richmond Hill Center")
					.latitude(43.8829)
					.longitude(-79.4400)
					.build();
			distributionCenterRepository.save(center4);

			// Add items with brands to distribution centers
			Item item1 = Item.builder()
					.name("Quarter zip")
					.brand(Item.Brand.PATAGONIA)
					.quantity(10)
					.distributionCenter(center1)
					.build();
			itemRepository.save(item1);

			Item item2 = Item.builder()
					.name("White shirt")
					.brand(Item.Brand.NORTH_FACE)
					.quantity(5)
					.distributionCenter(center2)
					.build();
			itemRepository.save(item2);

			Item item3 = Item.builder()
					.name("Black shirt")
					.brand(Item.Brand.PRADA)
					.quantity(8)
					.distributionCenter(center3)
					.build();
			itemRepository.save(item3);

			Item item4 = Item.builder()
					.name("Blue jeans")
					.brand(Item.Brand.LEVIS)
					.quantity(15)
					.distributionCenter(center3)
					.build();
			itemRepository.save(item4);

			Item item5 = Item.builder()
					.name("Red Shoes")
					.brand(Item.Brand.GUCCI)
					.quantity(15)
					.distributionCenter(center3)
					.build();
			itemRepository.save(item5);

			Item item6 = Item.builder()
					.name("Black shirt")
					.brand(Item.Brand.PRADA)
					.quantity(8)
					.distributionCenter(center2)
					.build();
			itemRepository.save(item6);
		};
	}

}
