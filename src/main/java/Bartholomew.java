import java.util.Objects;
import java.util.Scanner;

public class Bartholomew {
    public static void main(String[] args) {
        String name = "Bartholomew";
        System.out.println("Fine day we're having! I am " + name);
        System.out.println("What may I do for you?");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye Master! Hope to see you again soon!");
                break;
            }
            System.out.println(input);
        }
        scanner.close();
    }
}
