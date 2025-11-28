package k23cnt2.nvs_lesson8.repository;
import k23cnt2.nvs_lesson8.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
