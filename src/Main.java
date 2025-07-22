public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

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
        
    }
}