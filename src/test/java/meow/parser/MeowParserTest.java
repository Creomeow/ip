package meow.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import meow.command.CommandWord;
import meow.exception.MeowException;

public class MeowParserTest {

    @Test
    void parse_bye_success() throws Exception {
        ParsedInput p = MeowParser.parse("bye");
        assertEquals(CommandWord.BYE, p.command);
    }

    @Test
    void parse_list_success() throws Exception {
        ParsedInput p = MeowParser.parse("list");
        assertEquals(CommandWord.LIST, p.command);
    }

    @Test
    void parse_unknownCommand_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse("blah"));
        assertEquals("I'm sorry, but I don't know what that means :-(", e.getMessage());
    }

    @Test
    void parse_empty_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse(""));
        assertEquals("Please type a command.", e.getMessage());
    }

    @Test
    void parse_mark_validIndex_success() throws Exception {
        ParsedInput p = MeowParser.parse("mark 2");
        assertEquals(CommandWord.MARK, p.command);
        assertEquals(2, p.getIndex());
    }

    @Test
    void parse_unmark_validIndex_success() throws Exception {
        ParsedInput p = MeowParser.parse("unmark 3");
        assertEquals(CommandWord.UNMARK, p.command);
        assertEquals(3, p.getIndex());
    }

    @Test
    void parse_delete_validIndex_success() throws Exception {
        ParsedInput p = MeowParser.parse("delete 1");
        assertEquals(CommandWord.DELETE, p.command);
        assertEquals(1, p.getIndex());
    }

    @Test
    void parse_mark_missingIndex_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse("mark"));
        assertEquals("Please provide a task number. Example: mark 2", e.getMessage());
    }

    @Test
    void parse_mark_nonIntegerIndex_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse("mark two"));
        assertEquals("Please provide a valid number. Example: mark 2", e.getMessage());
    }

    @Test
    void parse_mark_zeroIndex_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse("mark 0"));
        assertEquals("Task number must be 1 or bigger.", e.getMessage());
    }

    @Test
    void parse_todo_success() throws Exception {
        ParsedInput p = MeowParser.parse("todo borrow book");
        assertEquals(CommandWord.TODO, p.command);
        assertEquals("borrow book", p.getDescription());
    }

    @Test
    void parse_todo_emptyDescription_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse("todo"));
        assertEquals("The description of a todo cannot be empty.", e.getMessage());
    }

    @Test
    void parse_deadline_success() throws Exception {
        ParsedInput p = MeowParser.parse("deadline return book /by 2019-10-15");
        assertEquals(CommandWord.DEADLINE, p.command);
        assertEquals("return book", p.getDescription());
        assertEquals(LocalDate.of(2019, 10, 15), p.getBy());
    }

    @Test
    void parse_deadline_missingBy_throws() {
        MeowException e = assertThrows(MeowException.class,
                () -> MeowParser.parse("deadline return book"));
        assertEquals("Deadline format: deadline <description> /by <yyyy-mm-dd>", e.getMessage());
    }

    @Test
    void parse_deadline_invalidDate_throws() {
        // message comes from DateTimeUtil.parseDate(...)
        assertThrows(MeowException.class,
                () -> MeowParser.parse("deadline return book /by 15-10-2019"));
    }

    @Test
    void parse_event_success() throws Exception {
        ParsedInput p = MeowParser.parse("event project meeting /from 2019-10-15 /to 2019-10-16");
        assertEquals(CommandWord.EVENT, p.command);
        assertEquals("project meeting", p.getDescription());
        assertEquals(LocalDate.of(2019, 10, 15), p.getStart());
        assertEquals(LocalDate.of(2019, 10, 16), p.getEnd());
    }

    @Test
    void parse_event_missingFrom_throws() {
        MeowException e = assertThrows(MeowException.class,
                () -> MeowParser.parse("event meeting /to 2019-10-16"));
        assertEquals("Event format: event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>", e.getMessage());
    }

    @Test
    void parse_event_missingTo_throws() {
        MeowException e = assertThrows(MeowException.class,
                () -> MeowParser.parse("event meeting /from 2019-10-15"));
        assertEquals("Event format: event <desc> /from <yyyy-mm-dd> /to <yyyy-mm-dd>", e.getMessage());
    }

    @Test
    void parse_event_invalidDate_throws() {
        assertThrows(MeowException.class,
                () -> MeowParser.parse("event meeting /from 2019-15-10 /to 2019-10-16"));
    }

    @Test
    void parse_find_noDescription_throws() {
        MeowException e = assertThrows(MeowException.class, () -> MeowParser.parse("find"));
        assertEquals("What am I supposed to find?", e.getMessage());
    }

    @Test
    void parse_find_success() throws Exception {
        ParsedInput p = MeowParser.parse("find book");
        assertEquals(CommandWord.FIND, p.command);
        assertEquals("book", p.getDescription());
    }
}

