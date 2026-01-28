package meow;

import meow.exception.MeowException;
import meow.parser.MeowParser;
import meow.parser.ParsedInput;
import meow.storage.Storage;
import meow.task.Deadline;
import meow.task.Event;
import meow.task.Task;
import meow.task.ToDo;
import meow.ui.Meowtput;

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

            try {
                ParsedInput parsed = MeowParser.parse(input);
                execute(parsed, meow, Meowtput, storage);
            } catch (MeowException e) {
                Meowtput.showError(e.getMessage());
            }
        }
    }

    private static void execute(
            ParsedInput parsed, Meow meow, Meowtput meowtput, Storage storage) throws MeowException {
        switch (parsed.command) {
            case BYE: {
                meow.exit();
                meowtput.goodbye();
                break;
            }

            case LIST: {
                meowtput.showTasks(meow.getTasks());
                break;
            }

            case TODO: {
                Task todo = new ToDo(parsed.description);
                meow.addTask(todo);
                storage.save(meow.getTasks());
                meowtput.showAddedTask(todo, meow.getTasks().size());
                break;
            }

            case DEADLINE: {
                Task deadline = new Deadline(parsed.description, parsed.by);
                meow.addTask(deadline);
                storage.save(meow.getTasks());
                meowtput.showAddedTask(deadline, meow.getTasks().size());
                break;
            }

            case EVENT: {
                Task event = new Event(parsed.description, parsed.start, parsed.end);
                meow.addTask(event);
                storage.save(meow.getTasks());
                meowtput.showAddedTask(event, meow.getTasks().size());
                break;
            }

            case MARK: {
                Task task = meow.getTask(parsed.index); // you’ll add this helper OR keep old logic
                task.markAsDone();
                storage.save(meow.getTasks());
                meowtput.showMarked(task);
                break;
            }

            case UNMARK: {
                Task task = meow.getTask(parsed.index);
                task.markAsUndone();
                storage.save(meow.getTasks());
                meowtput.showUnmarked(task);
                break;
            }

            case DELETE: {
                Task removed = meow.deleteTask(parsed.index);
                storage.save(meow.getTasks());
                meowtput.showDeletedTask(removed, meow.getTasks().size());
                break;
            }

            default:
                throw new MeowException("meow.Meow! Unknown command.");
        }
    }
}

