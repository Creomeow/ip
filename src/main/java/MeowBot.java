import java.util.Scanner;

public class MeowBot {
    public static void main (String[] args) {
        Meow meow = new Meow();
        Meowtput Meowtput = new Meowtput();
        Scanner sc = new Scanner(System.in);

        Meowtput.greeting();

        while(meow.isActive()) {
            String input = sc.nextLine().toLowerCase();

            if (input.equals("bye")) {
                meow.exit();
                Meowtput.goodbye();
            } else if (input.equals("list")) {
                Meowtput.showTasks(meow.getTasks());
            } else {
                meow.addTask(input);
                Meowtput.showAddedTask(input);
            }
        }
    }
}
