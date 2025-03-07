public class Ui {
    public void showWelcomeMessage() {
        System.out.println("Ah, what a splendid day! I am Bartholomew, at your esteemed service.");
        System.out.println("How may I be of assistance to you, Master?");
    }

    public void showGoodbyeMessage() {
        System.out.println("Farewell, Master. I wish you a most delightful rest of your day.");
    }

    public void showLoadingError() {
        System.out.println("It appears there are no prior records. Not to worry, we shall commence afresh!");
    }

    public void showTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("Master, your task list is presently empty. Perhaps a fresh endeavor is in order?");
            return;
        }
        System.out.println("Very well, here is the current inventory of your esteemed tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("A total of " + tasks.size() + " tasks reside in your list.");
    }

    public void showTaskAdded(Task task) {
        System.out.println("Splendid choice, Master! I have dutifully added the following task to your registry:\n  " + task);
    }

    public void showTaskRemoved(Task task, int remainingTasks) {
        System.out.println("As per your request, I have removed this task:\n  " + task);
        System.out.println("Now, your inventory of tasks stands at " + remainingTasks + ".");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Magnificent, Master! I have marked this task as completed:\n  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("Understood, Master. I have restored this task to its incomplete status:\n  " + task);
    }

    public void showInvalidCommand() {
        System.out.println("Ah, it seems that was not a recognised command. Might I suggest: list, todo, deadline, event, mark, unmark, delete?");
    }

    public void showError(String message) {
        System.out.println("My sincerest apologies, Master. " + message);
    }
}
