/**
 * Parses user input and delegates commands to the appropriate components.
 */
public class Parser {
    private static final String FIND_COMMAND = "find";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";

    /**
     * Checks if the input command is an exit command.
     *
     * @param input The user input command.
     * @return True if the command is "bye", otherwise false.
     */
    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Processes and executes the given user command.
     *
     * @param input  The user command.
     * @param tasks  The task list to modify.
     * @param ui     The UI handler to display messages.
     * @param storage The storage handler to save tasks.
     */
    public void handleCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        try {
            if (input.equalsIgnoreCase("list")) {
                ui.showTaskList(tasks);
            } else if (input.startsWith(TODO_COMMAND)) {
                String taskDescription = input.replaceFirst(TODO_COMMAND, "").trim();
                if (taskDescription.isEmpty()) {
                    throw new MissingDescriptorException();
                }
                tasks.addTask(new Todo(taskDescription), ui, storage);
            } else if (input.startsWith(DEADLINE_COMMAND)) {
                String[] parts = input.split(" /by ", 2);
                if (parts.length < 2 || parts[0].replaceFirst(DEADLINE_COMMAND, "").trim().isEmpty()) {
                    throw new MissingDescriptorException();
                }
                tasks.addTask(new Deadline(parts[0].replaceFirst(DEADLINE_COMMAND, "").trim(), parts[1].trim()), ui, storage);
            } else if (input.startsWith(EVENT_COMMAND)) {
                String[] parts = input.split(" /from ", 2);
                if (parts.length < 2) {
                    throw new MissingDescriptorException();
                }
                String[] timeParts = parts[1].split(" /to ", 2);
                if (timeParts.length < 2 || parts[0].replaceFirst(EVENT_COMMAND, "").trim().isEmpty()) {
                    throw new MissingDescriptorException();
                }
                tasks.addTask(new Event(parts[0].replaceFirst(EVENT_COMMAND, "").trim(), timeParts[0].trim(), timeParts[1].trim()), ui, storage);
            } else if (input.startsWith(MARK_COMMAND)) {
                tasks.markTask(input, ui, storage);
            } else if (input.startsWith(UNMARK_COMMAND)) {
                tasks.unmarkTask(input, ui, storage);
            } else if (input.startsWith(DELETE_COMMAND)) {
                tasks.deleteTask(input, ui, storage);
            } else if (input.startsWith(FIND_COMMAND)) {
                tasks.findTask(input, ui);
            } else {
                ui.showInvalidCommand();
            }
        } catch (MissingDescriptorException e) {
            ui.showError("Oops! A descriptor is missing. Please provide a valid task description.");
        }
    }

}
