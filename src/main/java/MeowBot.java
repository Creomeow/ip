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
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                Task todo = new ToDo(desc);
                meow.addTask(todo);
                Meowtput.showAddedTask(todo, meow.getTasks().size());
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                Task deadline = new Deadline(parts[0], parts[1]);
                meow.addTask(deadline);
                Meowtput.showAddedTask(deadline, meow.getTasks().size());
            } else if (input.startsWith("event ")) {
                String eventFull = input.substring(6);
                String[] parts = eventFull.split(" /from | /to ");
                Task event = new Event(parts[0], parts[1], parts[2]);
                meow.addTask(event);
                Meowtput.showAddedTask(event, meow.getTasks().size());
            } else {
                Task taskInput = new Task(input);
                meow.addTask(taskInput);
                Meowtput.showAddedTask(taskInput, meow.getTasks().size());
            }
        }
    }
}
