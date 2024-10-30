package com.example;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PagedModel;

import com.example.Repository.BillRepository;
import com.example.Repository.ProductItemRepository;
import com.example.Service.CustomerServiceClient;
import com.example.Service.InventoryServiceClient;
import com.example.entity.Bill;
import com.example.entity.Customer;
import com.example.entity.Product;
import com.example.entity.ProductItem;


@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
 
    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerServiceClient customerService,
                            InventoryServiceClient inventoryService)
    {
        return args -> {
            Customer c1=customerService.findCustomerById(1L);
            System.out.println("*****************");
            System.out.println("ID= "+c1.getId());
            System.out.println("Name= "+c1.getName());
            System.out.println("Email= "+c1.getEmail());
 
            Bill bill1=billRepository.save(new Bill());
 
            org.springframework.hateoas.PagedModel<Product> products = inventoryService.findAll();
            products.getContent().forEach(p->{
                productItemRepository.save(new ProductItem(null,p.getId(),null,p.getPrice(),90,bill1));
            });
 
 
    };
    }
}