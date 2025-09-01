import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class HistoryManagerTest {

    @Test
    void shouldPreserveTaskStateInHistory() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        Task originalTask = new Task(1, "Original", "Original description", Status.NEW);

        historyManager.add(originalTask);
        List<Task> history = historyManager.getHistory();
        Task historicalTask = history.get(0);

        assertEquals(originalTask.getId(), historicalTask.getId(), "ID должен сохраниться");
        assertEquals(originalTask.getTitle(), historicalTask.getTitle(), "Название должно сохраниться");
        assertEquals(originalTask.getDescription(), historicalTask.getDescription(), "Описание должно сохраниться");
        assertEquals(originalTask.getStatus(), historicalTask.getStatus(), "Статус должен сохраниться");
    }

    @Test
    void shouldLimitHistoryToMaxSize() {
        HistoryManager historyManager = Manager.getDefaultHistory();

        //добавляем больше элементов, чем максимальный размер
        for (int i = 1; i <= 15; i++) {
            historyManager.add(new Task(i, "Task " + i, "Description " + i, Status.NEW));
        }

        List<Task> history = historyManager.getHistory();

        assertEquals(10, history.size(), "История должна быть ограничена 10 элементами");
        assertEquals(6, history.get(0).getId(), "Самый старый элемент должен быть удален");
        assertEquals(15, history.get(9).getId(), "Самый новый элемент должен быть последним");
    }

    @Test
    void shouldAllowDuplicatesInHistory() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        Task task = new Task(1, "Task", "Description", Status.NEW);

        // добавляем одну и ту же задачу несколько раз
        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();

        assertEquals(3, history.size(), "Дубликаты должны сохраняться в истории");
        assertEquals(task.getId(), history.get(0).getId());
        assertEquals(task.getId(), history.get(1).getId());
        assertEquals(task.getId(), history.get(2).getId());
        }
}