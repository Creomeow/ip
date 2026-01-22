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
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                Task task = meow.getTasks().get(index);
                task.markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
                Meowtput.line();
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                Task task = meow.getTasks().get(index);
                task.markAsUndone();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
                Meowtput.line();
            } else {
                meow.addTask(input);
                Meowtput.showAddedTask(input);
            }
        }
    }
}
