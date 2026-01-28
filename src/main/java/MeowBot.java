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

        if (!meow.getTasks().isEmpty()) {
            Meowtput.showTasks(meow.getTasks());
        }

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
            if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                meowtput.line();
                throw new MeowException("Meow! Deadline format: deadline <desc> /by <yyyy-mm-dd>");
            }

            String description = parts[0];
            String by = parts[1];
            java.time.LocalDate byLocalDate = DateTimeUtil.parseDate(by);
            Task deadline = new Deadline(description, byLocalDate);
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

            String description = parts[0];
            String start = parts[1];
            String end = parts[2];
            java.time.LocalDate startLocalDate = DateTimeUtil.parseDate(start);
            java.time.LocalDate endLocalDate = DateTimeUtil.parseDate(end);


            Task event = new Event(description, startLocalDate, endLocalDate);
            meow.addTask(event);
            storage.save(meow.getTasks());
            meowtput.showAddedTask(event, meow.getTasks().size());
        } else if (input.startsWith("delete")) {
            String description = input.substring(7);
            if (description.isEmpty()) {
                meowtput.line();
                throw new MeowException("Meow! Please provide a task to delete.");
            }

            int oneBasedIndex;
            try {
                oneBasedIndex = Integer.parseInt(description);
            } catch (NumberFormatException e) {
                throw new MeowException("Meow! Please provide a valid number. Example: delete 3");
            }

            Task removed = meow.deleteTask(oneBasedIndex);
            storage.save(meow.getTasks());
            meowtput.showDeletedTask(removed, meow.getTasks().size());
        } else {
            meowtput.line();
            throw new MeowException("Meow! I'm sorry, but I don't know what that means :-(");
        }
    }
}

