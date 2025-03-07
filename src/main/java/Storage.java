import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;
    private static final String DIRECTORY_PATH = "./data/";

    public Storage(String filePath) {  // Add this constructor
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) dir.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No previous tasks found. Starting fresh!");
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = parseTask(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    private Task parseTask(String line) throws Exception {
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
            if (parts.length < 5) throw new Exception("Invalid Event format");
            return new Event(description, parts[3], parts[4], isDone);
        default:
            throw new Exception("Unknown task type");
        }
    }
}
