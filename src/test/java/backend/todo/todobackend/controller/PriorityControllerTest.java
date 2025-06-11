package backend.todo.todobackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.todo.todobackend.entity.Priority;
import backend.todo.todobackend.search.PrioritySearchValues;
import backend.todo.todobackend.service.PriorityService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityControllerTest {

    private Priority samplePriority;

    @BeforeEach
    void setUp() {
        samplePriority = new Priority();
        samplePriority.setId(1L);
        samplePriority.setTitle("High");
        samplePriority.setColor("#ff0000");
    }

    @Test
    void findAll_ReturnsList() {
        PriorityService stub = new PriorityService(null) {
            @Override
            public List<Priority> findAll(String email) {
                assertEquals("user@example.com", email);
                return Collections.singletonList(samplePriority);
            }
        };
        PriorityController controller = new PriorityController(stub);

        List<Priority> result = controller.findAll("user@example.com");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(samplePriority, result.get(0));
    }

    @Test
    void add_Success() {
        Priority toAdd = new Priority();
        toAdd.setTitle("Medium");
        toAdd.setColor("#00ff00");

        PriorityService stub = new PriorityService(null) {
            @Override
            public Priority add(Priority p) {
                assertSame(toAdd, p);
                return samplePriority;
            }
        };
        PriorityController controller = new PriorityController(stub);

        ResponseEntity<Priority> resp = controller.add(toAdd);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(samplePriority, resp.getBody());
    }

    @Test
    void add_InvalidId() {
        Priority toAdd = new Priority();
        toAdd.setId(5L);
        toAdd.setTitle("Low");
        toAdd.setColor("#0000ff");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<Priority> resp = controller.add(toAdd);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("redundant param: id MUST be null", resp.getBody());
    }

    @Test
    void add_MissingTitle() {
        Priority toAdd = new Priority();
        toAdd.setColor("#0000ff");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<Priority> resp = controller.add(toAdd);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("missed param: title", resp.getBody());
    }

    @Test
    void add_MissingColor() {
        Priority toAdd = new Priority();
        toAdd.setTitle("Low");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<Priority> resp = controller.add(toAdd);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("missed param: color", resp.getBody());
    }

    @Test
    void update_Success() {
        Priority toUpdate = new Priority();
        toUpdate.setId(2L);
        toUpdate.setTitle("Urgent");
        toUpdate.setColor("#ffff00");

        PriorityService stub = new PriorityService(null) {
            @Override
            public Priority update(Priority p) {
                assertSame(toUpdate, p);
                return p;
            }
        };
        PriorityController controller = new PriorityController(stub);

        ResponseEntity<?> resp = controller.update(toUpdate);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void update_MissingId() {
        Priority toUpdate = new Priority();
        toUpdate.setTitle("Urgent");
        toUpdate.setColor("#ffff00");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<?> resp = controller.update(toUpdate);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("missed param: id", resp.getBody());
    }

    @Test
    void update_MissingTitle() {
        Priority toUpdate = new Priority();
        toUpdate.setId(2L);
        toUpdate.setColor("#ffff00");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<?> resp = controller.update(toUpdate);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("missed param: title", resp.getBody());
    }

    @Test
    void update_MissingColor() {
        Priority toUpdate = new Priority();
        toUpdate.setId(2L);
        toUpdate.setTitle("Urgent");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<?> resp = controller.update(toUpdate);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("missed param: color", resp.getBody());
    }

    @Test
    void findById_Success() {
        PriorityService stub = new PriorityService(null) {
            @Override
            public Priority findById(Long id) {
                assertEquals(3L, id);
                return samplePriority;
            }
        };
        PriorityController controller = new PriorityController(stub);

        ResponseEntity<Priority> resp = controller.findById(3L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(samplePriority, resp.getBody());
    }

    @Test
    void findById_NotFound() {
        PriorityService stub = new PriorityService(null) {
            @Override
            public Priority findById(Long id) {
                throw new NoSuchElementException();
            }
        };
        PriorityController controller = new PriorityController(stub);

        ResponseEntity<Priority> resp = controller.findById(4L);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("id=4 not found", resp.getBody());
    }

    @Test
    void delete_Success() {
        PriorityService stub = new PriorityService(null) {
            @Override
            public void deleteById(Long id) {
                assertEquals(5L, id);
            }
        };
        PriorityController controller = new PriorityController(stub);
        ResponseEntity<?> resp = controller.delete(5L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void delete_NotFound() {
        PriorityService stub = new PriorityService(null) {
            @Override
            public void deleteById(Long id) {
                throw new EmptyResultDataAccessException(1);
            }
        };
        PriorityController controller = new PriorityController(stub);
        ResponseEntity<?> resp = controller.delete(6L);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("id=6 not found", resp.getBody());
    }

    @Test
    void search_Success() {
        PrioritySearchValues vals = new PrioritySearchValues();
        vals.setEmail("test@x.com");
        vals.setTitle("Low");

        PriorityService stub = new PriorityService(null) {
            @Override
            public List<Priority> find(String title, String email) {
                assertEquals("Low", title);
                assertEquals("test@x.com", email);
                return Collections.singletonList(samplePriority);
            }
        };
        PriorityController controller = new PriorityController(stub);

        ResponseEntity<List<Priority>> resp = controller.search(vals);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1, resp.getBody().size());
        assertEquals(samplePriority, resp.getBody().get(0));
    }

    @Test
    void search_MissingEmail() {
        PrioritySearchValues vals = new PrioritySearchValues();
        vals.setTitle("Low");

        PriorityController controller = new PriorityController(new PriorityService(null));
        ResponseEntity<List<Priority>> resp = controller.search(vals);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resp.getStatusCode());
        assertEquals("missed param: email", resp.getBody());
    }
}
