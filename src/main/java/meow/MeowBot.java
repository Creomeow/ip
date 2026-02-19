package meow;

import meow.exception.MeowException;
import meow.parser.MeowParser;
import meow.parser.ParsedInput;
import meow.storage.Storage;
import meow.ui.MeowOutput;

public class MeowBot {
    private final Meow meow;
    private final Storage storage;
    private final MeowOutput ui;

    public MeowBot() {
        this.meow = new Meow();
        this.storage = new Storage();
        this.ui = new MeowOutput();

        try {
            meow.setTasks(storage.load());
        } catch (MeowException e) {}
    }

    /** Returns chatbot response to user input (no printing). */
    public String getResponse(String input) {
        try {
            ParsedInput parsed = MeowParser.parse(input);
            return execute(parsed);
        } catch (MeowException e) {
            return ui.formatError(e.getMessage());
        }
    }

    /**
     * Returns the formatted greeting string produced by the UI layer. Intended for GUI callers.
     *
     * @return a greeting string (already contains surrounding lines)
     */
    public String getGreeting() {
        return ui.formatGreeting();
    }

    /**
     * Returns the formatted task list produced by the UI layer. Intended for GUI callers.
     *
     * @return the tasks listing (empty-list message when applicable)
     */

    public String getFormattedTasks() {
        return ui.formatTasks(meow.getTasks());
    }

    /**
     * Checks if the application should exit (user entered bye command).
     *
     * @return true if the bye command was executed, false otherwise
     */
    public boolean isExiting() {
        return !meow.isActive();
    }

    public String execute(ParsedInput parsed) throws MeowException {
        switch (parsed.getCommand()) {
        case BYE:
            meow.exit();
            return ui.formatGoodbye();

        case LIST:
            return ui.formatTasks(meow.getTasks());

        case TODO: {
            var todo = new meow.task.ToDo(parsed.getDescription());
            meow.addTask(todo);
            storage.save(meow.getTasks());
            return ui.formatAddedTask(todo, meow.getTasks().size());
        }

        case DEADLINE: {
            var deadline = new meow.task.Deadline(parsed.getDescription(), parsed.getBy());
            meow.addTask(deadline);
            storage.save(meow.getTasks());
            return ui.formatAddedTask(deadline, meow.getTasks().size());
        }

        case EVENT: {
             var event = new meow.task.Event(parsed.getDescription(),
                    parsed.getStart(), parsed.getEnd());
            meow.addTask(event);
            storage.save(meow.getTasks());
            return ui.formatAddedTask(event, meow.getTasks().size());
        }

        case MARK: {
            var task = meow.getTask(parsed.getIndex());
            task.markAsDone();
            storage.save(meow.getTasks());
            return ui.formatMarked(task);
        }

        case UNMARK: {
            var task = meow.getTask(parsed.getIndex());
            task.markAsUndone();
            storage.save(meow.getTasks());
            return ui.formatUnmarked(task);
        }

        case DELETE: {
            var removed = meow.deleteTask(parsed.getIndex());
            storage.save(meow.getTasks());
            return ui.formatDeletedTask(removed, meow.getTasks().size());
        }

        case FIND: {
            var matches = meow.findTaskIndices(parsed.getDescription());
            return ui.formatMatchingTasks(meow.getTasks(), matches);
        }

        default:
            throw new MeowException("Unknown command.");
        }
    }
}