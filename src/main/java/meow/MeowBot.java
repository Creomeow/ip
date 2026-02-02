package meow;

import java.util.Scanner;

import meow.exception.MeowException;
import meow.parser.MeowParser;
import meow.parser.ParsedInput;
import meow.storage.Storage;
import meow.task.Deadline;
import meow.task.Event;
import meow.task.Task;
import meow.task.ToDo;
import meow.ui.MeowOutput;

/**
 * Main entry point for the MeowBot application.
 * Manages the command loop, user interactions, and task execution.
 */

public class MeowBot {

    /**
     * Main method that starts the MeowBot application.
     * Initializes the task manager, loads saved tasks, and processes user commands in a loop.
     *
     * @param args command-line arguments (not used)
     */

    public static void main (String[] args) {
        Meow meow = new Meow();
        MeowOutput meowOutput = new MeowOutput();
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage();

        try {
            meow.setTasks(storage.load());
        } catch (MeowException e) {
            meowOutput.showError(e.getMessage());
        }

        meowOutput.greeting();

        if (!meow.getTasks().isEmpty()) {
            meowOutput.showTasks(meow.getTasks());
        }

        while (meow.isActive() && sc.hasNextLine()) {
            String input = sc.nextLine();

            try {
                ParsedInput parsed = MeowParser.parse(input);
                execute(parsed, meow, meowOutput, storage);
            } catch (MeowException e) {
                meowOutput.showError(e.getMessage());
            }
        }
    }

    /**
     * Executes the command specified in the parsed input.
     * Performs the appropriate task operation and updates storage and UI accordingly.
     *
     * @param parsed the parsed user command
     * @param meow the task manager instance
     * @param meowtput the output handler for user feedback
     * @param storage the storage handler for persisting tasks
     * @throws MeowException if command execution fails
     */

    private static void execute(
            ParsedInput parsed, Meow meow, MeowOutput meowtput, Storage storage) throws MeowException {
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
           Task todo = new ToDo(parsed.getDescription());
           meow.addTask(todo);
           storage.save(meow.getTasks());
           meowtput.showAddedTask(todo, meow.getTasks().size());
           break;
       }

       case DEADLINE: {
           Task deadline = new Deadline(parsed.getDescription(), parsed.getBy());
           meow.addTask(deadline);
           storage.save(meow.getTasks());
           meowtput.showAddedTask(deadline, meow.getTasks().size());
           break;
       }

       case EVENT: {
           Task event = new Event(parsed.getDescription(), parsed.getStart(), parsed.getEnd());
           meow.addTask(event);
           storage.save(meow.getTasks());
           meowtput.showAddedTask(event, meow.getTasks().size());
           break;
       }

       case MARK: {
           Task task = meow.getTask(parsed.getIndex());
           task.markAsDone();
           storage.save(meow.getTasks());
           meowtput.showMarked(task);
           break;
       }

       case UNMARK: {
           Task task = meow.getTask(parsed.getIndex());
           task.markAsUndone();
           storage.save(meow.getTasks());
           meowtput.showUnmarked(task);
           break;
       }

       case DELETE: {
           Task removed = meow.deleteTask(parsed.getIndex());
           storage.save(meow.getTasks());
           meowtput.showDeletedTask(removed, meow.getTasks().size());
           break;
       }

        case FIND: {
            var matches = meow.findTaskIndices(parsed.getDescription());
            meowtput.showMatchingTasks(meow.getTasks(), matches);
            break;
        }

       default:
           throw new MeowException("MEOW! Unknown command.");
       }
    }
}

