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

    public void showAddedTask(String task) {
        System.out.println("added: " + task);
    }

    public void showTasks(ArrayList<String> tasks) {
        if (tasks.isEmpty()) {
            line();
            System.out.println("You do not have any tasks.");
            line();
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}
