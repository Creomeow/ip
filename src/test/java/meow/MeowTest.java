package meow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import meow.task.ToDo;

public class MeowTest {

    @Test
    void findTaskIndices_matchesCorrectTasks() {
        Meow meow = new Meow();
        meow.addTask(new ToDo("read book"));
        meow.addTask(new ToDo("return book"));
        meow.addTask(new ToDo("join sports club"));

        ArrayList<Integer> matches = meow.findTaskIndices("BOOK");

        assertEquals(2, matches.size());
        assertEquals(0, matches.get(0));
        assertEquals(1, matches.get(1));
    }

    @Test
    void findTaskIndices_noMatches_returnsEmpty() {
        Meow meow = new Meow();
        meow.addTask(new ToDo("read book"));

        ArrayList<Integer> matches = meow.findTaskIndices("banana");

        assertTrue(matches.isEmpty());
    }
}