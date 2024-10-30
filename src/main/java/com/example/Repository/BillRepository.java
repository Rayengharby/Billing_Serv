package com.example.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.entity.Bill;

@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill, Long> {

	Bill save(Bill bill);

}

