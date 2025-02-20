import java.util.Scanner;

public class Bartholomew {
    private static final int MAX_TASKS = 100;
    private static final int INDEX_OFFSET = 1;
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";

    public static void main(String[] args) {
        System.out.println("Fine day we're having! I am Bartholomew, at your service!");
        System.out.println("What may I do for you?");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();  // Trim input first!

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("Goodbye Master! I bid you a jolly rest of your day!");
                break;
            } else if (input.equalsIgnoreCase(LIST_COMMAND)) {
                printTaskList(tasks, taskCount);
            } else if (input.startsWith(TODO_COMMAND)) {
                try {
                    String description = extractCommandArgument(input, TODO_COMMAND);
                    taskCount = addTodoTask(tasks, taskCount, description);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please include a task that you may wish to complete.");
                }
            } else if (input.startsWith(DEADLINE_COMMAND)) {
                try {
                    String details = extractCommandArgument(input, DEADLINE_COMMAND);
                    taskCount = addDeadlineTask(tasks, taskCount, details);
                } catch (MissingDescriptorException e) {
                    System.out.println("Invalid deadline format. Use: deadline <task> /by <date/time>");
                }
            } else if (input.startsWith(EVENT_COMMAND)) {
                try {
                    String details = extractCommandArgument(input, EVENT_COMMAND);
                    taskCount = addEventTask(tasks, taskCount, details);
                } catch (MissingDescriptorException e) {
                    System.out.println("Invalid event format. Use: event <task> /from <start> /to <end>");
                }
            } else if (input.startsWith(MARK_COMMAND)) {
                try {
                    String indexStr = extractCommandArgument(input, MARK_COMMAND);
                    markTask(tasks, taskCount, indexStr);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please specify a valid task number to mark as done.");
                }
            } else if (input.startsWith(UNMARK_COMMAND)) {
                try {
                    String indexStr = extractCommandArgument(input, UNMARK_COMMAND);
                    unmarkTask(tasks, taskCount, indexStr);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please specify a valid task number to unmark.");
                }
            } else {
                System.out.println("That is an invalid command! Try: list, todo, deadline, event, mark, unmark.");
            }
        }
        scanner.close();
    }

    private static String extractCommandArgument(String input, String command) throws MissingDescriptorException {
        // Ensure input starts with the full command and has space after it
        if (input.equals(command)) {
            throw new MissingDescriptorException();
        }
        String argument = input.substring(command.length()).trim();
        if (argument.isEmpty()) {
            throw new MissingDescriptorException();
        }
        return argument;
    }

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + INDEX_OFFSET) + ". " + tasks[i]);
        }
        System.out.println("You have " + taskCount + " tasks in your list!");
    }

    private static int addTodoTask(Task[] tasks, int taskCount, String description) {
        tasks[taskCount] = new Todo(description);
        System.out.println("Certainly. I have added the task:\n  " + tasks[taskCount]);
        return taskCount + INDEX_OFFSET;
    }

    private static int addDeadlineTask(Task[] tasks, int taskCount, String input) throws MissingDescriptorException {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new MissingDescriptorException();
        }
        tasks[taskCount] = new Deadline(parts[0].trim(), parts[1].trim());
        System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
        return taskCount + INDEX_OFFSET;
    }

    private static int addEventTask(Task[] tasks, int taskCount, String input) throws MissingDescriptorException {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new MissingDescriptorException();
        }
        tasks[taskCount] = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
        return taskCount + INDEX_OFFSET;
    }

    private static void markTask(Task[] tasks, int taskCount, String input) throws MissingDescriptorException {
        int index = parseTaskIndex(input, taskCount);
        tasks[index].markAsDone();
        System.out.println("Brilliant! I have marked this task as done:\n  " + tasks[index]);
    }

    private static void unmarkTask(Task[] tasks, int taskCount, String input) throws MissingDescriptorException {
        int index = parseTaskIndex(input, taskCount);
        tasks[index].unmarkAsDone();
        System.out.println("Alright! I have marked this task as not done yet:\n  " + tasks[index]);
    }

    private static int parseTaskIndex(String input, int taskCount) throws MissingDescriptorException {
        try {
            int index = Integer.parseInt(input) - INDEX_OFFSET;
            if (index < 0 || index >= taskCount) {
                throw new MissingDescriptorException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MissingDescriptorException();
        }
    }
}
