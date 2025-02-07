import java.util.Scanner;

public class Bartholomew {
    private static final int MAX_TASKS = 100;
    private static final int INDEX_OFFSET = 1;
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";

    public static void main(String[] args) {
        System.out.println("Fine day we're having! I am Bartholomew.");
        System.out.println("What may I do for you?");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("Goodbye Master! I bid you a jolly rest of your day!");
                break;
            } else if (input.equalsIgnoreCase(LIST_COMMAND)) {
                printTaskList(tasks, taskCount);
            } else if (input.startsWith(TODO_COMMAND)) {
                taskCount = addTodoTask(tasks, taskCount, input.substring(TODO_COMMAND.length()));
            } else if (input.startsWith(DEADLINE_COMMAND)) {
                taskCount = addDeadlineTask(tasks, taskCount, input.substring(DEADLINE_COMMAND.length()));
            } else if (input.startsWith(EVENT_COMMAND)) {
                taskCount = addEventTask(tasks, taskCount, input.substring(EVENT_COMMAND.length()));
            } else if (input.startsWith(MARK_COMMAND)) {
                markTask(tasks, taskCount, input.substring(MARK_COMMAND.length()));
            } else if (input.startsWith(UNMARK_COMMAND)) {
                unmarkTask(tasks, taskCount, input.substring(UNMARK_COMMAND.length()));
            } else {
                System.out.println("That is an invalid command! Try: list, todo, deadline, event, mark, unmark.");
            }
        }
        scanner.close();
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
        System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
        return taskCount + INDEX_OFFSET;
    }

    private static int addDeadlineTask(Task[] tasks, int taskCount, String input) {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            System.out.println("Invalid deadline format. Use: deadline <task> /by <date/time>");
            return taskCount;
        }
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
        return taskCount + INDEX_OFFSET;
    }

    private static int addEventTask(Task[] tasks, int taskCount, String input) {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            System.out.println("Invalid event format. Use: event <task> /from <start> /to <end>");
            return taskCount;
        }
        tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
        System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
        return taskCount + INDEX_OFFSET;
    }

    private static void markTask(Task[] tasks, int taskCount, String input) {
        int index = parseTaskIndex(input, taskCount);
        if (index == -1) return;
        tasks[index].markAsDone();
        System.out.println("Brilliant! I have marked this task as done:\n  " + tasks[index]);
    }

    private static void unmarkTask(Task[] tasks, int taskCount, String input) {
        int index = parseTaskIndex(input, taskCount);
        if (index == -1) return;
        tasks[index].unmarkAsDone();
        System.out.println("Alright! I have marked this task as not done yet:\n  " + tasks[index]);
    }

    private static int parseTaskIndex(String input, int taskCount) {
        try {
            int index = Integer.parseInt(input) - INDEX_OFFSET;
            if (index < 0 || index >= taskCount) {
                System.out.println("That is an invalid task number.");
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            System.out.println("That is an invalid task number.");
            return -1;
        }
    }
}