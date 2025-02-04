import java.util.Objects;
import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        String name = "Bartholomew";
        System.out.println("Fine day we're having! I am " + name + ".");
        System.out.println("What may I do for you, master?");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye Master! I bid you a jolly rest of your day!");
                break;
            }

            else if (command.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list, master:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            }

            else if (command.equalsIgnoreCase("mark") || command.equalsIgnoreCase("unmark")) {
                if (parts.length < 2) {
                    System.out.println("Kindly specify a task number, master.");
                    continue;
                }

                try {
                    int index = Integer.parseInt(parts[1]) - 1;

                    if (index < 0 || index >= taskCount) {
                        System.out.println("That is an invalid task number, master.");
                        continue;
                    }

                    if (command.equalsIgnoreCase("mark")) {
                        tasks[index].markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Delightful, master! I have marked this task as done!");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________");
                    } else {
                        tasks[index].markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Absolutely, master! I have marked this task as incomplete.");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("That is an invalid task number format, master");
                }
            }

            else {
                if (taskCount < 100) {
                    tasks[taskCount] = new Task(input);
                    System.out.println("added: " + tasks[taskCount]);
                    taskCount++;
                } else {
                    System.out.println("The task list is full, master!");
                }
            }
        }
        scanner.close();
    }
}

