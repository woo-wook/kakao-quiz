package com.kakao.insurance.repository.product;

import com.kakao.insurance.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
