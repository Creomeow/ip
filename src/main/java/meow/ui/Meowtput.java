package meow.ui;

import meow.task.Task;

import java.util.ArrayList;

public class Meowtput {

    public void line() {
        System.out.println("______________________________________");
    }

    public void greeting() {
        line();
        System.out.println("Hello! I'm meow.Meow");
        System.out.println("What can I do for you?");
        line();
    }

    public void goodbye() {
        line();
        System.out.println("Bye. Hope to see you again soon!");
        line();
    }

    public void showAddedTask(Task task, int taskCount) {
        line();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        line();
    }

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

    public void showError(String message) {
        line();
        System.out.println(" OOPS! " + message);
        line();
    }

    public void showMarked(Task task) {
        line();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        line();
    }

    public void showUnmarked(Task task) {
        line();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        line();
    }

    public void showDeletedTask(Task task, int newTaskCount) {
        line();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + newTaskCount + " tasks in the list.");
        line();
    }


}
