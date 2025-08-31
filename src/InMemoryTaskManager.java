import java.util.*;

public class InMemoryTaskManager {
    private int nextId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private int generateId() {
        return nextId++;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Task createTask(Task task) {
        if (task == null) return null;
        int id = generateId();
        Task newTask = new Task(id, task.getTitle(), task.getDescription(),
                task.getStatus());
        tasks.put(id, newTask);
        return newTask;
    }

    public void updateTask (Task task) {
        if (task != null && tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Epic createEpic (Epic epic) {
        if (epic == null) return null;
        int id = generateId();
        Epic newEpic = new Epic(id, epic.getTitle(), epic.getDescription());
        epics.put(id, newEpic);
        return newEpic;
    }

    public void updateEpic(Epic epic) {
        if (epic != null && epics.containsKey(epic.getId())) {
            Epic savedEpic = epics.get(epic.getId());
            savedEpic.setTitle(epic.getTitle());
            savedEpic.setDescription(epic.getDescription());
        }
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public List<Subtask>getAllSubtask() {
        return  new ArrayList<>(subtasks.values());
    }

    public void deleteAllSubtask() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic.getId());
        }
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Subtask createSubtask(Subtask subtask) {
        if (subtask == null) return null;
        int epidId = subtask.getEpicId();
        if (!epics.containsKey(epidId)) return null;
        int id = generateId();
        Subtask newSubtask = new Subtask(id, subtask.getTitle(),
                subtask.getDescription(), subtask.getStatus(), epidId);
        subtasks.put(id, newSubtask);
        Epic epic = epics.get(epidId);
        epic.addSubtasksId(id);
        updateEpicStatus(epidId);
        return newSubtask;
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic.getId());
            }
        }
    }

    public List<Subtask>getEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return Collections.emptyList();
        List<Subtask> result = new ArrayList<>();
        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                result.add(subtask);
            }
        }
        return result;
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;
        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        boolean allNew = true;
        boolean allDone = true;
        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) continue;
            Status status = subtask.getStatus();
            if (status != Status.NEW) allNew = false;
            if (status != Status.DONE) allDone = false;
        }
        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
