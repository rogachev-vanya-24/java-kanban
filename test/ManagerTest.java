import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultShouldReturnReadyToWorkTaskManager() {
        TaskManager manager = Manager.getDefault();

        assertNotNull(manager, "Менеджер не должен быть null");

        // Проверим, что менеджер готов к работе
        assertNotNull(manager.getAllTasks(), "Должен возвращаться список задач");
        assertNotNull(manager.getAllEpics(), "Должен возвращаться список эпиков");
        assertNotNull(manager.getAllSubtasks(), "Должен возвращаться список подзадач");
    }

    @Test
    void getDefaultHistoryShouldReturnReadyToWorkHistoryManager() {
        HistoryManager historyManager = Manager.getDefaultHistory();

        assertNotNull(historyManager, "Менеджер истории не должен быть null");
        assertNotNull(historyManager.getHistory(), "Должен возвращаться список истории");
    }

    @Test
    void multipleCallsShouldReturnDifferentInstances() {
        TaskManager manager1 = Manager.getDefault();
        TaskManager manager2 = Manager.getDefault();
        HistoryManager history1 = Manager.getDefaultHistory();
        HistoryManager history2 = Manager.getDefaultHistory();

        assertNotSame(manager1, manager2, "Должны возвращаться разные экземпляры менеджера");
        assertNotSame(history1, history2, "Должны возвращаться разные экземпляры менеджера истории");
    }
}