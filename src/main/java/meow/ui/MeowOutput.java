package meow.ui;

import java.util.ArrayList;

import meow.task.Task;

/**
 * Output handler for the MeowBot application.
 * Manages all user interface messages and task display formatting.
 */

public class MeowOutput {

    /**
     * Prints a decorative line separator to the console.
     */

    public void line() {
        System.out.println("______________________________________");
    }

    /**
     * Displays the greeting message when the application starts.
     */

    public void greeting() {
        line();
        System.out.println("Hello! I'm Meow");
        System.out.println("What can I do for you?");
        line();
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */

    public void goodbye() {
        line();
        System.out.println("Bye. Hope to see you again soon!");
        line();
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task the task that was added
     * @param taskCount the total number of tasks after adding
     */

    public void showAddedTask(Task task, int taskCount) {
        line();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        line();
    }

    /**
     * Displays all tasks in the task list.
     * Shows a message if the list is empty.
     *
     * @param tasks the list of tasks to display
     */

    public void showTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            line();
            System.out.println("You do not have any tasks.");
            line();
            return;
        }

        line();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */

    public void showError(String message) {
        line();
        System.out.println(" MEOW! " + message);
        line();
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task the task that was marked as done
     */

    public void showMarked(Task task) {
        line();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        line();
    }

    /**
     * Displays a message confirming that a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */

    public void showUnmarked(Task task) {
        line();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        line();
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task the task that was deleted
     * @param newTaskCount the total number of tasks after deletion
     */

    public void showDeletedTask(Task task, int newTaskCount) {
        line();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + newTaskCount + " tasks in the list.");
        line();
    }

    /**
     * Displays the tasks that match a search query.
     *
     * @param tasks the list of all tasks
     * @param matchedIndices the indices of tasks that match the search query
     */

    public void showMatchingTasks(ArrayList<Task> tasks, ArrayList<Integer> matchedIndices) {
        line();

        if (matchedIndices.isEmpty()) {
            System.out.println("No matching tasks found.");
            line();
            return;
        }

        System.out.println("Here are the matching tasks in your list:");
        for (int idx : matchedIndices) {
            System.out.println((idx + 1) + "." + tasks.get(idx));
        }

        line();
    }
}
