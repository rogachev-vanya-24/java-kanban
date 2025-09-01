import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void shouldAddAndFindDifferentTaskTypesById() {
        TaskManager manager = Manager.getDefault();

        Task task = manager.createTask(new Task(0, "Task", "Description", Status.NEW));
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Description"));
        Subtask subtask = manager.createSubtask(
                new Subtask(0, "Subtask", "Description", Status.NEW, epic.getId())
        );

        assertNotNull(manager.getTask(task.getId()), "Задача должна находиться по id");
        assertNotNull(manager.getEpic(epic.getId()), "Эпик должен находиться по id");
        assertNotNull(manager.getSubtask(subtask.getId()), "Подзадача должна находиться по id");
    }

    @Test
    void shouldHandleManualAndGeneratedIdsWithoutConflicts() {
        TaskManager manager = Manager.getDefault();

        // создаем задачу с "ручным" id (эмулируем ситуацию)
        Task manualIdTask = new Task(100, "Manual ID", "Description", Status.NEW);
        // createTask игнорирует переданный id и генерирует свой

        Task createdTask = manager.createTask(manualIdTask);

        assertNotEquals(100, createdTask.getId(),
                "Менеджер должен игнорировать переданный id и генерировать свой");
        assertNotNull(manager.getTask(createdTask.getId()),
                "Задача с сгенерированным id должна быть доступна");
    }

    @Test
    void shouldNotConflictWhenAddingTasksWithDifferentIds() {
        TaskManager manager = Manager.getDefault();

        Task task1 = manager.createTask(new Task(0, "Task 1", "Description 1", Status.NEW));
        Task task2 = manager.createTask(new Task(0, "Task 2", "Description 2", Status.NEW));

        assertNotEquals(task1.getId(), task2.getId(),
                "Задачи должны иметь разные id");
        assertNotNull(manager.getTask(task1.getId()),
                "Первая задача должна быть доступна");
        assertNotNull(manager.getTask(task2.getId()),
                "Вторая задача должна быть доступна");
    }
}