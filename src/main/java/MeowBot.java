import java.util.Scanner;

public class MeowBot {
    public static void main (String[] args) {
        Meow meow = new Meow();
        Meowtput Meowtput = new Meowtput();
        Scanner sc = new Scanner(System.in);

        Meowtput.greeting();

        while (meow.isActive()) {
            String input = sc.nextLine().toLowerCase();
            try {
                handle(input, meow, Meowtput);
            } catch (MeowException e) {
                Meowtput.showError(e.getMessage());
            }
        }
    }

    private static void handle(String input, Meow meow, Meowtput meowtput) throws MeowException {
        switch(input) {
            case "bye":
                meow.exit();
                meowtput.goodbye();
            case "list":
                meowtput.showTasks(meow.getTasks());
            case "mark": {
                int index = Integer.parseInt(input.substring(5)) - 1;
                if (index < 0 || index > meow.getTasks().size()) {
                    meowtput.line();
                    throw new MeowException("Meow! That task number doesn't exist.");
                }

                Task task = meow.getTasks().get(index);
                task.markAsDone();
                meowtput.showMarked(task);
            }
            case "unmark": {
                int index = Integer.parseInt(input.substring(7)) - 1;
                if (index < 0 || index > meow.getTasks().size()) {
                    meowtput.line();
                    throw new MeowException("Meow! That task number doesn't exist.");
                }

                Task task = meow.getTasks().get(index);
                task.markAsUndone();
                meowtput.showUnmarked(task);
            }
            case "todo ":
                String desc = input.substring(5);
                if (desc.isEmpty()) {
                    meowtput.line();
                    throw new MeowException("Meow! The description of a todo cannot be empty.");
                }

                Task todo = new ToDo(desc);
                meow.addTask(todo);
                meowtput.showAddedTask(todo, meow.getTasks().size());
            case "deadline ": {
                String[] parts = input.substring(9).split(" /by ");
                if (parts[1].isEmpty()) {
                    meowtput.line();
                    throw new MeowException("Meow! The deadline cannot be empty.");
                }

                Task deadline = new Deadline(parts[0], parts[1]);
                meow.addTask(deadline);
                meowtput.showAddedTask(deadline, meow.getTasks().size());
            }
            case "event ": {
                String eventFull = input.substring(6);
                String[] parts = eventFull.split(" /from | /to ");
                if (parts[1].isEmpty()) {
                    meowtput.line();
                    throw new MeowException("Meow! The start of an event cannot be empty.");
                }

                if (parts[2].isEmpty()) {
                    meowtput.line();
                    throw new MeowException("Meow! The end of an event cannot be empty.");
                }

                Task event = new Event(parts[0], parts[1], parts[2]);
                meow.addTask(event);
                meowtput.showAddedTask(event, meow.getTasks().size());
            }
            default:
                meowtput.line();
                throw new MeowException("Meow! I'm sorry, but I don't know what that means :-( ");
        }
    }
}
