import java.util.Scanner;

public class MeowBot {
    public static void main (String[] args) {
        Meow meow = new Meow();
        Meowtput Meowtput = new Meowtput();
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage();

        try {
            meow.setTasks(storage.load());
        } catch (MeowException e) {
            Meowtput.showError(e.getMessage());
        }

        Meowtput.greeting();

        while (meow.isActive() && sc.hasNextLine()) {
            String input = sc.nextLine();
            String lower = input.toLowerCase();
            try {
                handle(input, lower, meow, Meowtput, storage);
            } catch (MeowException e) {
                Meowtput.showError(e.getMessage());
            }
        }
    }

    private static void handle(String input, String lower, Meow meow,
                               Meowtput meowtput, Storage storage) throws MeowException {
        if (input.startsWith("bye")) {
            meow.exit();
            meowtput.goodbye();
        } else if (input.startsWith("list")) {
            meowtput.showTasks(meow.getTasks());
        } else if (input.startsWith("mark")) {
           int index = Integer.parseInt(input.substring(5)) - 1;
           if (index < 0 || index >= meow.getTasks().size()) {
               meowtput.line();
               throw new MeowException("Meow! That task number doesn't exist.");
           }

           Task task = meow.getTasks().get(index);
           task.markAsDone();
           storage.save(meow.getTasks());
           meowtput.showMarked(task);
        } else if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            if (index < 0 || index >= meow.getTasks().size()) {
                meowtput.line();
                throw new MeowException("Meow! That task number doesn't exist.");
            }

            Task task = meow.getTasks().get(index);
            task.markAsUndone();
            storage.save(meow.getTasks());
            meowtput.showUnmarked(task);
        } else if (input.startsWith("todo")) {
            String desc = input.substring(5);
            if (desc.isEmpty()) {
                meowtput.line();
                throw new MeowException("Meow! The description of a todo cannot be empty.");
            }

            Task todo = new ToDo(desc);
            meow.addTask(todo);
            storage.save(meow.getTasks());
            meowtput.showAddedTask(todo, meow.getTasks().size());
        } else if (input.startsWith("deadline")) {
            String[] parts = input.substring(9).split(" /by ");
            if (parts[1].isEmpty()) {
                meowtput.line();
                throw new MeowException("Meow! The deadline cannot be empty.");
            }

            Task deadline = new Deadline(parts[0], parts[1]);
            meow.addTask(deadline);
            storage.save(meow.getTasks());
            meowtput.showAddedTask(deadline, meow.getTasks().size());
        } else if (input.startsWith("event")) {
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
            storage.save(meow.getTasks());
            meowtput.showAddedTask(event, meow.getTasks().size());
        } else if (input.startsWith("delete")) {
            String desc = input.substring(7);
            if (desc.isEmpty()) {
                meowtput.line();
                throw new MeowException("Meow! Please provide a task to delete.");
            }

            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            if (index < 0 || index >= meow.getTasks().size()) {
                meowtput.line();
                throw new MeowException("Meow! That task number doesn't exist.");
            }

            Task removed = meow.getTasks().remove(index);
            storage.save(meow.getTasks());
            meowtput.showDeletedTask(removed, meow.getTasks().size());
        } else {
            meowtput.line();
            throw new MeowException("Meow! I'm sorry, but I don't know what that means :-(");
        }
    }
}

