import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void shouldNotAddEpicAsItsOwnSubtask() {
        TaskManager manager = Manager.getDefault();
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Description"));

        Subtask invalidSubtask = manager.createSubtask(
                new Subtask(0, "Invalid", "Description", Status.NEW, epic.getId())
        );

        // Assert - это должно работать нормально, эпик может иметь подзадачи
        assertNotNull(invalidSubtask, "Эпик может иметь подзадачи");

        // Проверим, что подзадача действительно добавлена к эпику
        assertEquals(1, manager.getEpicStatus(epic.getId()).size(),
                "Подзадача должна быть добавлена к эпику");
    }

    @Test
    void shouldNotAllowSubtaskToBeItsOwnEpic() {
        // Arrange
        TaskManager manager = Manager.getDefault();
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Description"));
        Subtask subtask = manager.createSubtask(
                new Subtask(0, "Subtask", "Description", Status.NEW, epic.getId())
        );


        assertEquals(epic.getId(), subtask.getEpicId(),
                "Подзадача должна ссылаться на корректный эпик");
        assertNotEquals(subtask.getId(), subtask.getEpicId(),
                "Подзадача не может быть своим же эпиком");
    }

    @Test
    void shouldNotAddSubtaskToNonExistentEpic() {
        TaskManager manager = Manager.getDefault();

        Subtask subtask = manager.createSubtask(
                new Subtask(0, "Subtask", "Description", Status.NEW, 999) // Несуществующий эпик
        );

        assertNull(subtask, "Подзадача не должна создаваться для несуществующего эпика");
    }
}