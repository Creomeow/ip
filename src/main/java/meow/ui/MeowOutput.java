package meow.ui;

import meow.task.Task;
import java.util.ArrayList;

public class MeowOutput {

    private static final String LINE = "______________________________________\n";

    public String formatGreeting() {
        return LINE
                + "Hello! I'm Meow\n"
                + "What can I do for you?\n"
                + LINE;
    }

    public String formatGoodbye() {
        return LINE
                + "Bye. Hope to see you again soon!\n"
                + LINE;
    }

    public String formatError(String message) {
        return LINE
                + " MEOW! " + message + "\n"
                + LINE;
    }

    public String formatAddedTask(Task task, int taskCount) {
        return LINE
                + "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.\n"
                + LINE;
    }

    public String formatTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return LINE + "You do not have any tasks.\n" + LINE;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(LINE);
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        sb.append(LINE);
        return sb.toString();
    }

    public String formatMarked(Task task) {
        return LINE
                + "Nice! I've marked this task as done:\n"
                + "  " + task + "\n"
                + LINE;
    }

    public String formatUnmarked(Task task) {
        return LINE
                + "OK, I've marked this task as not done yet:\n"
                + "  " + task + "\n"
                + LINE;
    }

    public String formatDeletedTask(Task task, int newCount) {
        return LINE
                + "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + newCount + " tasks in the list.\n"
                + LINE;
    }

    public String formatMatchingTasks(ArrayList<Task> tasks, ArrayList<Integer> indices) {
        if (indices.isEmpty()) {
            return LINE + "No matching tasks found.\n" + LINE;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(LINE);
        sb.append("Here are the matching tasks in your list:\n");
        for (int idx : indices) {
            sb.append(idx + 1).append(".").append(tasks.get(idx)).append("\n");
        }
        sb.append(LINE);
        return sb.toString();
    }
}
