package backend.todo.todobackend.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.requests.TaskSearchValues;
import backend.todo.todobackend.service.TaskService;

import java.text.ParseException;
import java.util.*;

public class TaskControllerTest {

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Sample Task");
        sampleTask.setCompleted(false);
    }

    @Test
    void findAll_ReturnsList() {
        TaskService stubService = new TaskService(null) {
            @Override
            public List<Task> findAll(String email) {
                assertEquals("user@example.com", email);
                return Collections.singletonList(sampleTask);
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<List<Task>> response = controller.findAll("user@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleTask, response.getBody().get(0));
    }


    @Test
    void add_Success() {
        Task toAdd = new Task();
        toAdd.setTitle("New Task");
        toAdd.setCompleted(false);

        TaskService stubService = new TaskService(null) {
            @Override
            public Task add(Task task) {
                assertSame(toAdd, task);
                return sampleTask;
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<Task> response = controller.add(toAdd);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleTask, response.getBody());
    }

    @Test
    void add_InvalidId() {
        Task toAdd = new Task();
        toAdd.setId(10L);
        toAdd.setTitle("Title");

        TaskController controller = new TaskController(new TaskService(null));
        ResponseEntity<?> response = controller.add(toAdd);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("redundant param: id MUST be null", response.getBody());
    }

    @Test
    void add_MissingTitle() {
        Task toAdd = new Task();

        TaskController controller = new TaskController(new TaskService(null));
        ResponseEntity<?> response = controller.add(toAdd);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: title", response.getBody());
    }

    @Test
    void update_Success() {
        Task toUpdate = new Task();
        toUpdate.setId(1L);
        toUpdate.setTitle("Updated");

        TaskService stubService = new TaskService(null) {
            @Override
            public Task update(Task task) {
                assertSame(toUpdate, task);
                return task;
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<?> response = controller.update(toUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_MissingId() {
        Task toUpdate = new Task();
        toUpdate.setTitle("No ID");

        TaskController controller = new TaskController(new TaskService(null));
        ResponseEntity<?> response = controller.update(toUpdate);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: id", response.getBody());
    }

    @Test
    void update_MissingTitle() {
        Task toUpdate = new Task();
        toUpdate.setId(1L);

        TaskController controller = new TaskController(new TaskService(null));
        ResponseEntity<?> response = controller.update(toUpdate);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: title", response.getBody());
    }

    @Test
    void delete_Success() {
        TaskService stubService = new TaskService(null) {
            @Override
            public void deleteById(Long id) {}
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<?> response = controller.delete(2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete_NotFound() {
        TaskService stubService = new TaskService(null) {
            @Override
            public void deleteById(Long id) {
                throw new EmptyResultDataAccessException(1);
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<?> response = controller.delete(3L);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("id=3 not found", response.getBody());
    }

    @Test
    void findById_Success() {
        TaskService stubService = new TaskService(null) {
            @Override
            public Task findById(Long id) {
                assertEquals(4L, id);
                return sampleTask;
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<Task> response = controller.findById(4L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleTask, response.getBody());
    }

    @Test
    void findById_NotFound() {
        TaskService stubService = new TaskService(null) {
            @Override
            public Task findById(Long id) {
                throw new NoSuchElementException();
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<?> response = controller.findById(5L);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("id=5 not found", response.getBody());
    }

    @Test
    void search_Success() throws ParseException {
        TaskSearchValues values = new TaskSearchValues();
        values.setEmail("a@b.com");
        values.setPageNumber(0);
        values.setPageSize(10);
        values.setSortColumn("id");       // ← ADD THIS
        values.setSortDirection("asc");   // ← AND THIS

        // stub out the service:
        TaskService stubService = new TaskService(null) {
            @Override
            public Page<Task> findByParams(String text, Boolean completed, Long priorityId,
                                           Long categoryId, String email, Date dateFrom,
                                           Date dateTo, PageRequest paging) {
                assertNull(text);
                assertFalse(completed);
                assertEquals("a@b.com", email);
                // you could also assert that paging.getSort().getOrderFor("id") != null
                return new PageImpl<>(Collections.singletonList(sampleTask));
            }
        };
        TaskController controller = new TaskController(stubService);

        ResponseEntity<Page<Task>> response = controller.search(values);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void search_MissingEmail() throws ParseException {
        TaskController controller = new TaskController(new TaskService(null));
        TaskSearchValues vals = new TaskSearchValues();

        ResponseEntity<?> response = controller.search(vals);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("missed param: email", response.getBody());
    }
}
