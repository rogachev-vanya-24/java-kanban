import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskInheritanceTest {

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", "Description 1", Status.NEW, 10);
        Subtask subtask2 = new Subtask(1, "Subtask 2", "Description 2", Status.DONE, 20);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны");
        assertEquals(subtask1.hashCode(), subtask2.hashCode(), "Хэш-коды подзадач с одинаковым id должны совпадать");
    }

    @Test
    void epicWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic(1, "Epic 1", "Description 1");
        Epic epic2 = new Epic(1, "Epic 2", "Description 2");

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны");
        assertEquals(epic1.hashCode(), epic2.hashCode(), "Хэш-коды эпиков с одинаковым id должны совпадать");
    }

    @Test
    void differentTaskTypesWithSameIdShouldNotBeEqual() {

        Task task = new Task(1, "Task", "Description", Status.NEW);
        Subtask subtask = new Subtask(1, "Subtask", "Description", Status.NEW, 10);
        Epic epic = new Epic(1, "Epic", "Description");

        assertNotEquals(task, subtask, "Задача и подзадача не должны быть равны, даже с одинаковым id");
        assertNotEquals(task, epic, "Задача и эпик не должны быть равны, даже с одинаковым id");
        assertNotEquals(subtask, epic, "Подзадача и эпик не должны быть равны, даже с одинаковым id");
    }
}