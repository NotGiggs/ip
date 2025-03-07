import java.util.Scanner;

/**
 * The main class for Bartholomew, the refined task assistant.
 * It initializes components and orchestrates the program's flow.
 */
public class Bartholomew {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs a new Bartholomew instance.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Bartholomew(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop for handling user commands.
     */
    public void run() {
        ui.showWelcomeMessage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            if (parser.isExitCommand(input)) {
                ui.showGoodbyeMessage();
                break;
            }
            parser.handleCommand(input, tasks, ui, storage);
        }
        scanner.close();
    }

    /**
     * The program entry point.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Bartholomew("./data/Bartholomew.txt").run();
    }
}
