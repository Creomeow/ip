import java.util.ArrayList;

public class Meowtput {

    public void line() {
        System.out.println("______________________________________");
    }

    public void greeting() {
        line();
        System.out.println("Hello! I'm Meow");
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
        System.out.println("Got it. I've added this task: ");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        line();
    }

    public void showTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            line();
            System.out.println("You do not have any tasks.");
            line();
        }

        line();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        line();
    }
}
