package com.devmaster.lesson07.repository;
import com.devmaster.lesson07.entity.Category;
import
        org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Long> {
}
package com.devmaster.lesson07.repository;
import com.devmaster.lesson07.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long> {
}