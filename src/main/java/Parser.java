public class Parser {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";

    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase(EXIT_COMMAND);
    }

    public void handleCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        if (input.equalsIgnoreCase(LIST_COMMAND)) {
            ui.showTaskList(tasks);
        } else if (input.startsWith(TODO_COMMAND)) {
            tasks.addTodoTask(input, ui, storage);
        } else if (input.startsWith(DEADLINE_COMMAND)) {
            tasks.addDeadlineTask(input, ui, storage);
        } else if (input.startsWith(EVENT_COMMAND)) {
            tasks.addEventTask(input, ui, storage);
        } else if (input.startsWith(MARK_COMMAND)) {
            tasks.markTask(input, ui, storage);
        } else if (input.startsWith(UNMARK_COMMAND)) {
            tasks.unmarkTask(input, ui, storage);
        } else if (input.startsWith(DELETE_COMMAND)) {
            tasks.deleteTask(input, ui, storage);
        } else {
            ui.showInvalidCommand();
        }
    }
}
