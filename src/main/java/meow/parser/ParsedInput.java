package meow.parser;

import java.time.LocalDate;

import meow.command.CommandWord;

/**
 * Represents a parsed user command with its associated parameters.
 * Contains the command type and relevant data for task operations.
 */

public class ParsedInput {
    public final CommandWord command;

    private final String description;
    private final int index;
    private final LocalDate by;
    private final LocalDate start;
    private final LocalDate end;

    public CommandWord getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public int getIndex() {
        return index;
    }

    public LocalDate getBy() {
        return by;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    /**
     * Constructs a ParsedInput instance with command and optional parameters.
     *
     * @param command the type of command to execute
     * @param description the task description (if applicable)
     * @param index the task index (1-based, or -1 if not applicable)
     * @param by the deadline date (for deadline tasks)
     * @param start the start date (for event tasks)
     * @param end the end date (for event tasks)
     */

    public ParsedInput(CommandWord command, String description, int index,
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
