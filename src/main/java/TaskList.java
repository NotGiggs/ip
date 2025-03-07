import java.util.ArrayList;

/**
 * Manages the list of tasks, including adding, removing, marking, and searching for tasks.
 */
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

    /**
     * Adds a new task to the list.
     */
    public void addTask(Task task, Ui ui, Storage storage) {
        if (task.description.isEmpty()) {
            ui.showError("Please provide a task description.");
            return;
        }
        tasks.add(task);
        ui.showTaskAdded(task);
        storage.saveTasks(tasks);
    }

    /**
     * Marks a task as done.
     */
    public void markTask(String input, Ui ui, Storage storage) {
        int index = parseTaskIndex(input, ui);
        if (index == -1) return;

        tasks.get(index).markAsDone();
        ui.showTaskMarked(tasks.get(index));
        storage.saveTasks(tasks);
    }

    /**
     * Unmarks a task (sets it to incomplete).
     */
    public void unmarkTask(String input, Ui ui, Storage storage) {
        int index = parseTaskIndex(input, ui);
        if (index == -1) return;

        tasks.get(index).unmarkAsDone();
        ui.showTaskUnmarked(tasks.get(index));
        storage.saveTasks(tasks);
    }

    /**
     * Deletes a task from the list.
     */
    public void deleteTask(String input, Ui ui, Storage storage) {
        int index = parseTaskIndex(input, ui);
        if (index == -1) return;

        Task removedTask = tasks.remove(index);
        ui.showTaskRemoved(removedTask, tasks.size());
        storage.saveTasks(tasks);
    }

    /**
     * Searches for tasks containing a keyword in the description, deadline, or event timing.
     *
     * @param input The user's command containing the keyword.
     * @param ui    The user interface for displaying results.
     */
    public void findTask(String input, Ui ui) {
        String keyword = input.replaceFirst("find", "").trim();
        if (keyword.isEmpty()) {
            ui.showError("Might I trouble you to provide a search keyword?");
            return;
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Check if keyword is in description
            if (task.description.contains(keyword)) {
                matchingTasks.add(task);
            }
            // Check deadlines
            else if (task instanceof Deadline && ((Deadline) task).getBy().contains(keyword)) {
                matchingTasks.add(task);
            }
            // Check event timings
            else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFrom().contains(keyword) || event.getTo().contains(keyword)) {
                    matchingTasks.add(task);
                }
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showError("Regrettably, Master, I found no tasks matching your esteemed request.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }
    }


    /**
     * Parses the task index from the user input.
     */
    private int parseTaskIndex(String input, Ui ui) {
        String numberPart = input.replaceAll("[^0-9]", "").trim();

        if (numberPart.isEmpty()) {
            ui.showError("Please specify a valid task number.");
            return -1;
        }

        try {
            int index = Integer.parseInt(numberPart) - 1;
            if (index < 0 || index >= tasks.size()) {
                ui.showError("Task number out of range. Please enter a valid task number.");
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            ui.showError("Invalid task number format. Please enter a valid number.");
            return -1;
        }
    }
}
