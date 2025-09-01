import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Manager.getDefault();

        Task task1 = manager.createTask(new Task(0, "Task 1", "Description 1", Status.NEW));
        Task task2 = manager.createTask(new Task(0, "Task 2", "Description 2", Status.NEW));

        Epic epic1 = manager.createEpic(new Epic(0, "Epic 1", "Description Epic 1"));
        Epic epic2 = manager.createEpic(new Epic(0, "Epic 2", "Description Epic 2"));

        Subtask subtask1 = manager.createSubtask(
                new Subtask(0, "Subtask 1", "Description Subtask 1", Status.NEW, epic1.getId()));
        Subtask subtask2 = manager.createSubtask(
                new Subtask(0, "Subtask 2", "Description Subtask 2", Status.NEW, epic1.getId()));
        Subtask subtask3 = manager.createSubtask(
                new Subtask(0, "Subtask 3", "Description Subtask 3", Status.NEW, epic2.getId()));

        System.out.println("История после создания: " + manager.getHistory().size());

        manager.getTask(task1.getId());
        manager.getEpic(epic1.getId());
        manager.getSubtask(subtask1.getId());

        System.out.println("История после просмотра: " + manager.getHistory().size());
        List<Task> history = manager.getHistory();
        for (Task task : history) {
            System.out.println("Просмотрено: " + task.getTitle());
        }

        manager.getTask(task2.getId());
        manager.getEpic(epic2.getId());
        manager.getSubtask(subtask2.getId());

        System.out.println("Вся история: " + manager.getHistory().size());
        for (Task task : manager.getHistory()) {
            System.out.println("Просмотрено: " + task.getTitle());
        }
    }
}