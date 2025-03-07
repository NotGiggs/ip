import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void addTodoTask(String input, Ui ui, Storage storage) {
        String description = input.replaceFirst("todo", "").trim();
        if (description.isEmpty()) {
            ui.showError("Please include a task description for 'todo'.");
            return;
        }
        Task task = new Todo(description);
        tasks.add(task);
        ui.showTaskAdded(task);
        storage.saveTasks(tasks);
    }

    public void addDeadlineTask(String input, Ui ui, Storage storage) {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            ui.showError("Invalid deadline format. Use: deadline <task> /by <date/time>");
            return;
        }
        Task task = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(task);
        ui.showTaskAdded(task);
        storage.saveTasks(tasks);
    }

    public void addEventTask(String input, Ui ui, Storage storage) {
        String[] parts = input.split(" /from ", 2);
        if (parts.length < 2) {
            ui.showError("Invalid event format. Use: event <task> /from <start> /to <end>");
            return;
        }

        String[] timeParts = parts[1].split(" /to ", 2);
        if (timeParts.length < 2 || parts[0].trim().isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            ui.showError("Invalid event format. Use: event <task> /from <start> /to <end>");
            return;
        }

        Task task = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
        tasks.add(task);
        ui.showTaskAdded(task);
        storage.saveTasks(tasks);
    }

    public void markTask(String input, Ui ui, Storage storage) {
        int index = parseTaskIndex(input, ui);
        if (index == -1) return;

        tasks.get(index).markAsDone();
        ui.showTaskMarked(tasks.get(index));
        storage.saveTasks(tasks);
    }

    public void unmarkTask(String input, Ui ui, Storage storage) {
        int index = parseTaskIndex(input, ui);
        if (index == -1) return;

        tasks.get(index).unmarkAsDone();
        ui.showTaskUnmarked(tasks.get(index));
        storage.saveTasks(tasks);
    }

    public void deleteTask(String input, Ui ui, Storage storage) {
        int index = parseTaskIndex(input, ui);
        if (index == -1) return;

        Task removedTask = tasks.remove(index);
        ui.showTaskRemoved(removedTask, tasks.size());
        storage.saveTasks(tasks);
    }

    private int parseTaskIndex(String input, Ui ui) {
        String numberPart = input.replaceAll("[^0-9]", "").trim();

        if (numberPart.isEmpty()) {
            ui.showError("It appears you have not specified a task number. Might I trouble you to provide one?");
            return -1;
        }

        try {
            int index = Integer.parseInt(numberPart) - 1;
            if (index < 0 || index >= tasks.size()) {
                ui.showError("Regrettably, the task number you provided does not exist. Please specify a valid task.");
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            ui.showError("Ah, I believe that was not a valid numerical input. Kindly provide a proper number.");
            return -1;
        }
    }

}
