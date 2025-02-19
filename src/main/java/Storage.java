import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/Bartholomew.txt";
    private static final String DIRECTORY_PATH = "./data/";

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            File dir = new File(DIRECTORY_PATH);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn’t exist
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n"); // Save in the expected format
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No previous tasks found. Starting fresh!");
            return tasks;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = parseTask(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    private static Task parseTask(String line) throws Exception {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new Exception("Invalid task format");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            return new Todo(description, isDone);
        case "D":
            if (parts.length < 4) throw new Exception("Invalid Deadline format");
            return new Deadline(description, parts[3], isDone);
        case "E":
            if (parts.length < 4) throw new Exception("Invalid Event format");
            return new Event(parts[0].trim(), parts[3].trim(), parts[4].trim(), isDone);

        default:
            throw new Exception("Unknown task type");
        }
    }
}
