package com.example.ResetController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.BillRepository;
import com.example.Repository.ProductItemRepository;
import com.example.Service.CustomerServiceClient;
import com.example.Service.InventoryServiceClient;
import com.example.entity.Bill;


@RestController
public class BillRestController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @GetMapping("/bills/full/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id) {
        Bill bill = billRepository.findById(id).orElse(null);
        if (bill != null) {
            bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerID()));
            bill.setProductItems(productItemRepository.findByBillId(id));
            bill.getProductItems().forEach(pi -> {
                pi.setProduct(inventoryServiceClient.findProductById(pi.getProductID()));
            });
        }
        return bill;
    }
}
