import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {

        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(1, "Task 2", "Description 2", Status.IN_PROGRESS);


        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны");
        assertEquals(task1.hashCode(), task2.hashCode(), "Хэш-коды задач с одинаковым id должны совпадать");
    }

    @Test
    void tasksWithDifferentIdShouldNotBeEqual() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(2, "Task 1", "Description 1", Status.NEW);

        assertNotEquals(task1, task2, "Задачи с разным id не должны быть равны");
    }

    @Test
    void taskShouldRemainUnchangedAfterAddingToManager() {
        TaskManager manager = Manager.getDefault();
        Task originalTask = new Task(0, "Original", "Original description", Status.NEW);


        Task createdTask = manager.createTask(originalTask);
        Task retrievedTask = manager.getTask(createdTask.getId());


        assertNotNull(retrievedTask, "Задача должна быть найдена");
        assertEquals("Original", retrievedTask.getTitle(), "Название задачи должно сохраниться");
        assertEquals("Original description", retrievedTask.getDescription(), "Описание задачи должно сохраниться");
        assertEquals(Status.NEW, retrievedTask.getStatus(), "Статус задачи должен сохраниться");}
}