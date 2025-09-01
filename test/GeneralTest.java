import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneralTest {

    @Test
    void comprehensiveTaskManagerTest() {
        // Arrange
        TaskManager manager = Manager.getDefault();

        // Создаем задачи разных типов
        Task task = manager.createTask(new Task(0, "Task", "Description", Status.NEW));
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Description"));
        Subtask subtask = manager.createSubtask(
                new Subtask(0, "Subtask", "Description", Status.NEW, epic.getId())
        );

        // Проверяем создание
        assertNotNull(task, "Задача должна быть создана");
        assertNotNull(epic, "Эпик должен быть создан");
        assertNotNull(subtask, "Подзадача должна быть создана");

        // Проверяем поиск по id
        assertEquals(task, manager.getTask(task.getId()), "Задача должна находиться по id");
        assertEquals(epic, manager.getEpic(epic.getId()), "Эпик должен находиться по id");
        assertEquals(subtask, manager.getSubtask(subtask.getId()), "Подзадача должна находиться по id");

        // Проверяем историю просмотров
        assertEquals(3, manager.getHistory().size(), "В истории должно быть 3 просмотра");

        // Проверяем, что задачи сохранили свои свойства
        Task retrievedTask = manager.getTask(task.getId());
        assertEquals("Task", retrievedTask.getTitle(), "Название задачи должно сохраниться");
        assertEquals(Status.NEW, retrievedTask.getStatus(), "Статус задачи должен сохраниться");

        // Проверяем связь эпика и подзадачи
        assertEquals(1, manager.getEpicStatus(epic.getId()).size(),
                "Эпик должен содержать одну подзадачу");
        assertEquals(subtask.getId(), manager.getEpicStatus(epic.getId()).get(0).getId(),
                "Подзадача должна быть связана с эпиком");
    }
}