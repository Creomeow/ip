package meow.parser;

import java.time.LocalDate;

import meow.util.DateTimeUtil;
import meow.exception.MeowException;

/**
 * Parser for user input commands in the MeowBot application.
 * Converts raw user input strings into structured ParsedInput objects.
 */

public class MeowParser {

    /**
     * Parses a user input string into a ParsedInput object.
     *
     * @param input the raw user input string
     * @return a ParsedInput object representing the parsed command
     * @throws MeowException if the input is empty or contains an invalid command
     */

    public static ParsedInput parse(String input) throws MeowException {
        if (input.isEmpty()) {
            throw new MeowException("Please type a command.");
        }

        String[] parts = input.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String args = (parts.length == 2) ? parts[1].trim() : "";

        switch (commandWord) {
        case "bye":
            return ParsedInput.bye();

        case "list":
            return ParsedInput.list();

        case "mark":
            return ParsedInput.mark(parseIndex(args, "mark"));

        case "unmark":
            return ParsedInput.unmark(parseIndex(args, "unmark"));

        case "delete":
            return ParsedInput.delete(parseIndex(args, "delete"));

        case "todo":
            if (args.isEmpty()) {
                throw new MeowException("The description of a todo cannot be empty.");
            }
            return ParsedInput.todo(args);

        case "deadline":
            return parseDeadline(args);

        case "event":
            return parseEvent(args);

        default:
            throw new MeowException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses and validates a task index from the provided arguments.
     *
     * @param args the argument string containing the task number
     * @param cmd the command name (used in error messages)
     * @return the parsed task index (1-based)
     * @throws MeowException if args is empty, not a valid number, or is zero or negative
     */

    private static int parseIndex(String args, String cmd) throws MeowException {
        if (args.isEmpty()) {
            throw new MeowException("Please provide a task number. Example: " + cmd + " 2");
        }
        try {
            int index = Integer.parseInt(args);
            if (index <= 0) {
                throw new MeowException("Task number must be 1 or bigger.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MeowException("Please provide a valid number. Example: " + cmd + " 2");
        }
    }

    /**
     * Parses deadline command arguments into a deadline task.
     *
     * @param args the argument string in format "description /by yyyy-mm-dd"
     * @return a ParsedInput object for the deadline task
     * @throws MeowException if format is invalid or date cannot be parsed
     */

    private static ParsedInput parseDeadline(String args) throws MeowException {
        String[] dParts = args.split("\\s*/by\\s*", 2);
        if (args.isEmpty() || dParts.length < 2 || dParts[0].isEmpty() || dParts[1].isEmpty()) {
            throw new MeowException("Deadline format: deadline <description> /by <yyyy-mm-dd>");
        }

        String description = dParts[0].trim();
        LocalDate by = DateTimeUtil.parseDate(dParts[1].trim());
        return ParsedInput.deadline(description, by);
    }

    /**
     * Parses event command arguments into an event task.
     *
     * @param args the argument string in format "description /from yyyy-mm-dd /to yyyy-mm-dd"
     * @return a ParsedInput object for the event task
     * @throws MeowException if format is invalid or dates cannot be parsed
     */

    private static ParsedInput parseEvent(String args) throws MeowException {
        String[] fromParts = args.split("\\s*/from\\s*", 2);
        if (args.isEmpty() || fromParts.length < 2 || fromParts[0].isEmpty()) {
            throw new MeowException("Event format: event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
        }

        String description = fromParts[0];
        String rest = fromParts[1];
        String[] toParts = rest.split("\\s*/to\\s*", 2);

        if (toParts.length < 2 || toParts[0].isEmpty() || toParts[1].isEmpty()) {
            throw new MeowException("Event format: event <desc> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
        }

        LocalDate start = DateTimeUtil.parseDate(toParts[0]);
        LocalDate end = DateTimeUtil.parseDate(toParts[1]);
        return ParsedInput.event(description, start, end);
    }
}
