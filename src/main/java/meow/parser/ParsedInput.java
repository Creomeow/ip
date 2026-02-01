package meow.parser;

import meow.command.CommandWord;

import java.time.LocalDate;

/**
 * Represents parsed user input containing command information and associated data.
 *
 * This immutable class encapsulates the result of parsing user commands,
 * storing the command type, description, index, and optional date parameters.
 */

public class ParsedInput {
    public final CommandWord command;

    public final String description;
    public final int index;
    public final LocalDate by;
    public final LocalDate start;
    public final LocalDate end;

    /**
     * Constructs a ParsedInput with the specified command and parameters.
     *
     * @param command the command type to execute
     * @param description the task description (may be null)
     * @param index the one-based task index (or -1 if not applicable)
     * @param by the deadline date for deadline tasks (may be null)
     * @param start the start date for event tasks (may be null)
     * @param end the end date for event tasks (may be null)
     */
    
    private ParsedInput(CommandWord command, String description, int index,
                        LocalDate by, LocalDate start, LocalDate end) {
        this.command = command;
        this.description = description;
        this.index = index;
        this.by = by;
        this.start = start;
        this.end = end;
    }

    public static ParsedInput bye() {
        return new ParsedInput(CommandWord.BYE, null, -1, null, null, null);
    }

    public static ParsedInput list() {
        return new ParsedInput(CommandWord.LIST, null, -1, null, null, null);
    }

    public static ParsedInput mark(int oneBasedIndex) {
        return new ParsedInput(CommandWord.MARK, null, oneBasedIndex, null, null, null);
    }

    public static ParsedInput unmark(int oneBasedIndex) {
        return new ParsedInput(CommandWord.UNMARK, null, oneBasedIndex, null, null, null);
    }

    public static ParsedInput delete(int oneBasedIndex) {
        return new ParsedInput(CommandWord.DELETE, null, oneBasedIndex, null, null, null);
    }

    public static ParsedInput todo(String description) {
        return new ParsedInput(CommandWord.TODO, description, -1, null, null, null);
    }

    public static ParsedInput deadline(String description, LocalDate by) {
        return new ParsedInput(CommandWord.DEADLINE, description, -1, by, null, null);
    }

    public static ParsedInput event(String description, LocalDate start, LocalDate end) {
        return new ParsedInput(CommandWord.EVENT, description, -1, null, start, end);
    }
}
