package backend.todo.todobackend.controller;

import backend.todo.todobackend.DTO.EmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.search.CategorySearchValues;
import backend.todo.todobackend.service.CategoryService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryControllerTest {

    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        sampleCategory = new Category();
        sampleCategory.setId(1L);
        sampleCategory.setTitle("Sample Category");
    }

    @Test
    void findAll_ReturnsList() {
        CategoryService stubService = new CategoryService(null) {
            @Override
            public List<Category> findAll(String email) {
                assertEquals("user@example.com", email);
                return Collections.singletonList(sampleCategory);
            }
        };
        CategoryController controller = new CategoryController(stubService);
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("user@example.com");
        List<Category> result = controller.findAll(emailRequest);;
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleCategory, result.get(0));
    }

    @Test
    void add_Success() {
        Category toAdd = new Category();
        toAdd.setTitle("New Cat");

        CategoryService stubService = new CategoryService(null) {
            @Override
            public Category add(Category category) {
                assertSame(toAdd, category);
                return sampleCategory;
            }
        };
        CategoryController controller = new CategoryController(stubService);

        ResponseEntity<Category> response = controller.add(toAdd);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleCategory, response.getBody());
    }

    @Test
    void add_InvalidId() {
        Category toAdd = new Category();
        toAdd.setId(5L);
        toAdd.setTitle("Title");

        CategoryController controller = new CategoryController(new CategoryService(null));
        ResponseEntity<Category> response = controller.add(toAdd);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("redundant param: id MUST be null", response.getBody());
    }

    @Test
    void add_MissingTitle() {
        Category toAdd = new Category();

        CategoryController controller = new CategoryController(new CategoryService(null));
        ResponseEntity<Category> response = controller.add(toAdd);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: title MUST be not null", response.getBody());
    }

    @Test
    void update_Success() {
        Category toUpdate = new Category();
        toUpdate.setId(1L);
        toUpdate.setTitle("Updated");

        CategoryService stubService = new CategoryService(null) {
            @Override
            public Category update(Category category) {
                assertSame(toUpdate, category);
                return category;
            }
        };
        CategoryController controller = new CategoryController(stubService);

        ResponseEntity<?> response = controller.update(toUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_MissingId() {
        Category toUpdate = new Category();
        toUpdate.setTitle("No ID");

        CategoryController controller = new CategoryController(new CategoryService(null));
        ResponseEntity<?> response = controller.update(toUpdate);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: id", response.getBody());
    }

    @Test
    void update_MissingTitle() {
        Category toUpdate = new Category();
        toUpdate.setId(1L);

        CategoryController controller = new CategoryController(new CategoryService(null));
        ResponseEntity<?> response = controller.update(toUpdate);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: title", response.getBody());
    }

    @Test
    void delete_Success() {
        CategoryService stubService = new CategoryService(null) {
            @Override
            public void deleteById(Long id) {
                assertEquals(2L, id);
            }
        };
        CategoryController controller = new CategoryController(stubService);
        ResponseEntity<?> response = controller.delete(2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete_NotFound() {
        CategoryService stubService = new CategoryService(null) {
            @Override
            public void deleteById(Long id) {
                throw new EmptyResultDataAccessException(1);
            }
        };
        CategoryController controller = new CategoryController(stubService);
        ResponseEntity<?> response = controller.delete(3L);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("id=3 not found", response.getBody());
    }

    @Test
    void search_Success() {
        CategorySearchValues vals = new CategorySearchValues();
        vals.setEmail("a@b.com");
        vals.setTitle("foo");

        CategoryService stubService = new CategoryService(null) {
            @Override
            public List<Category> findByTitle(String title, String email) {
                assertEquals("foo", title);
                assertEquals("a@b.com", email);
                return Collections.singletonList(sampleCategory);
            }
        };
        CategoryController controller = new CategoryController(stubService);

        ResponseEntity<List<Category>> response = controller.search(vals);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleCategory, response.getBody().get(0));
    }

    @Test
    void search_MissingEmail() {
        CategorySearchValues vals = new CategorySearchValues();
        vals.setTitle("foo");

        CategoryController controller = new CategoryController(new CategoryService(null));
        ResponseEntity<List<Category>> response = controller.search(vals);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: email", response.getBody());
    }

    @Test
    void findById_Success() {
        CategoryService stubService = new CategoryService(null) {
            @Override
            public Category findById(Long id) {
                assertEquals(4L, id);
                return sampleCategory;
            }
        };
        CategoryController controller = new CategoryController(stubService);

        ResponseEntity<Category> response = controller.findById(4L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleCategory, response.getBody());
    }

    @Test
    void findById_NotFound() {
        CategoryService stubService = new CategoryService(null) {
            @Override
            public Category findById(Long id) {
                throw new NoSuchElementException();
            }
        };
        CategoryController controller = new CategoryController(stubService);

        ResponseEntity<Category> response = controller.findById(5L);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("id=5 not found", response.getBody());
    }
}
