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

    public void showTasks(ArrayList<String> list) {
        if (list.isEmpty()) {
            line();
            System.out.println("You do not have any tasks.");
            line();
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
}
