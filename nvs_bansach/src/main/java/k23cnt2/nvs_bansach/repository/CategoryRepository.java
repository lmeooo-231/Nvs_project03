package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}