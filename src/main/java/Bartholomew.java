import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        String name = "Bartholomew";
        System.out.println("Fine day we're having! I am " + name + ".");
        System.out.println("What may I do for you?");
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye Master! I bid you a jolly rest of your day!");
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println("You have " + taskCount + " tasks in your list!");
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
                taskCount++;
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                if (parts.length < 2) {
                    System.out.println("That is an invalid deadline format. Use: deadline <task> /by <date/time>");
                    continue;
                }
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
                taskCount++;
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ", 3);
                if (parts.length < 3) {
                    System.out.println("That is an invalid event format. Use: event <task> /from <start> /to <end>");
                    continue;
                }
                tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
                System.out.println("Certainly. I have added this task:\n  " + tasks[taskCount]);
                taskCount++;
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markAsDone();
                    System.out.println("Brilliant! I have marked this task as done:\n  " + tasks[index]);
                } else {
                    System.out.println("That is an invalid task number.");
                }
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].unmarkAsDone();
                    System.out.println("Alright! I have marked this task as not done yet:\n  " + tasks[index]);
                } else {
                    System.out.println("That is an invalid task number.");
                }
            } else {
                System.out.println("That is an invalid command! Try the following commands: list, todo, deadline, event, mark, unmark.");
            }
        }

        scanner.close();
    }
}
