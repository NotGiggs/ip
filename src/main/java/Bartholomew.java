import java.util.ArrayList;
import java.util.Scanner;

public class Bartholomew {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";

    public static void main(String[] args) {
        System.out.println("Fine day we're having! I am Bartholomew, at your service!");
        System.out.println("What may I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = Storage.loadTasks();  // Load tasks from file

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("Goodbye Master! I bid you a jolly rest of your day!");
                break;
            } else if (input.equalsIgnoreCase(LIST_COMMAND)) {
                printTaskList(tasks);
            } else if (input.startsWith(TODO_COMMAND)) {
                try {
                    String description = extractCommandArgument(input, TODO_COMMAND);
                    addTodoTask(tasks, description);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please include a task that you may wish to complete.");
                }
            } else if (input.startsWith(DEADLINE_COMMAND)) {
                try {
                    String details = extractCommandArgument(input, DEADLINE_COMMAND);
                    addDeadlineTask(tasks, details);
                } catch (MissingDescriptorException e) {
                    System.out.println("Invalid deadline format. Use: deadline <task> /by <date/time>");
                }
            } else if (input.startsWith(EVENT_COMMAND)) {
                try {
                    String details = extractCommandArgument(input, EVENT_COMMAND);
                    addEventTask(tasks, details);
                } catch (MissingDescriptorException e) {
                    System.out.println("Invalid event format. Use: event <task> /from <start> /to <end>");
                }
            } else if (input.startsWith(MARK_COMMAND)) {
                try {
                    String indexStr = extractCommandArgument(input, MARK_COMMAND);
                    markTask(tasks, indexStr);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please specify a valid task number to mark as done.");
                }
            } else if (input.startsWith(UNMARK_COMMAND)) {
                try {
                    String indexStr = extractCommandArgument(input, UNMARK_COMMAND);
                    unmarkTask(tasks, indexStr);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please specify a valid task number to unmark.");
                }
            } else if (input.startsWith(DELETE_COMMAND)) {
                try {
                    String indexStr = extractCommandArgument(input, DELETE_COMMAND);
                    deleteTask(tasks, indexStr);
                } catch (MissingDescriptorException e) {
                    System.out.println("Please specify a valid task number to delete.");
                }
            } else {
                System.out.println("That is an invalid command! Try: list, todo, deadline, event, mark, unmark, delete.");
            }
        }
        scanner.close();
    }

    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("You have " + tasks.size() + " tasks in your list!");
    }

    private static void addTodoTask(ArrayList<Task> tasks, String description) {
        Task task = new Todo(description);
        tasks.add(task);
        System.out.println("Certainly. I have added the task:\n  " + task);
        Storage.saveTasks(tasks);
    }

    private static void addDeadlineTask(ArrayList<Task> tasks, String input) throws MissingDescriptorException {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new MissingDescriptorException();
        }
        Task task = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(task);
        System.out.println("Certainly. I have added this task:\n  " + task);
        Storage.saveTasks(tasks);
    }

    private static void addEventTask(ArrayList<Task> tasks, String input) throws MissingDescriptorException {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new MissingDescriptorException();
        }
        Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.add(task);
        System.out.println("Certainly. I have added this task:\n  " + task);
        Storage.saveTasks(tasks);
    }

    private static void markTask(ArrayList<Task> tasks, String input) throws MissingDescriptorException {
        int index = parseTaskIndex(input, tasks.size());
        tasks.get(index).markAsDone();
        System.out.println("Brilliant! I have marked this task as done:\n  " + tasks.get(index));
        Storage.saveTasks(tasks);
    }

    private static void unmarkTask(ArrayList<Task> tasks, String input) throws MissingDescriptorException {
        int index = parseTaskIndex(input, tasks.size());
        tasks.get(index).unmarkAsDone();
        System.out.println("Alright! I have marked this task as not done yet:\n  " + tasks.get(index));
        Storage.saveTasks(tasks);
    }

    private static void deleteTask(ArrayList<Task> tasks, String input) throws MissingDescriptorException {
        int index = parseTaskIndex(input, tasks.size());
        Task removedTask = tasks.remove(index);
        System.out.println("Noted. I've removed this task:\n  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        Storage.saveTasks(tasks);
    }

    private static int parseTaskIndex(String input, int taskCount) throws MissingDescriptorException {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= taskCount) {
                throw new MissingDescriptorException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MissingDescriptorException();
        }
    }

    private static String extractCommandArgument(String input, String command) throws MissingDescriptorException {
        if (input.equals(command)) {
            throw new MissingDescriptorException();
        }
        String argument = input.substring(command.length()).trim();
        if (argument.isEmpty()) {
            throw new MissingDescriptorException();
        }
        return argument;
    }
}
