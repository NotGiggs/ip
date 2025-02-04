import java.util.Objects;
import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        String name = "Bartholomew";
        System.out.println("Fine day we're having! I am " + name + ".");
        System.out.println("What may I do for you?");
        Scanner scanner = new Scanner(System.in);
        String[] task = new String[100];
        int i = 0;

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye Master! I bid you a jolly rest of your day!");
                break;
            }
            if (input.equalsIgnoreCase("list")) {
                for (int j = 0; j < i; j++) {
                    System.out.println(j+1 + "." + task[j]);
                }
            }
            else {
                task[i] = input;
                System.out.println("added: " + task[i]);
                i++;
            }

        }
        scanner.close();
    }
}
