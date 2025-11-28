package k23cnt2.nvs_lesson8.service;
import k23cnt2.nvs_lesson8.entity.Author;
import k23cnt2.nvs_lesson8.entity.Book;
import k23cnt2.nvs_lesson8.repository.AuthorRepository;
import
        org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    public Author saveAuthor(Author author) {
        return (Author) authorRepository.save(author);
    }
    public Author getAuthorById(Long id) {
        return (Author) authorRepository.findById(id).orElse(null);
    }
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    public List<Author> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }
}