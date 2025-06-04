package backend.todo.todobackend.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// возможные значения, по которым можно искать приоритеты
public class PrioritySearchValues {

    private String title;
    private String email;

}
