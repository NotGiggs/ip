import java.util.Scanner;

public class Bartholomew {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

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

    public static void main(String[] args) {
        new Bartholomew("./data/Bartholomew.txt").run();
    }
}
