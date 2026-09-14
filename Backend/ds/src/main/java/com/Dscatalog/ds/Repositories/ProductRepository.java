package com.Dscatalog.ds.Repositories;

import com.Dscatalog.ds.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
