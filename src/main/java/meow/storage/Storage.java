package meow.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.time.LocalDate;

import meow.task.Deadline;
import meow.task.Event;
import meow.task.Task;
import meow.task.ToDo;
import meow.exception.MeowException;

/**
 * Handles persistent storage of tasks for the MeowBot application.
 * Manages loading tasks from and saving tasks to a file.
 */

public class Storage {
    private final Path filePath;

    /**
     * Constructs a Storage instance with the default file path `data/meow.txt`.
     */

    public Storage() {
        this.filePath = Paths.get("data", "meow.txt");
    }

    /**
     * Loads tasks from the storage file.
     * Creates an empty task list if the file does not exist.
     *
     * @return an ArrayList of tasks loaded from the file
     * @throws MeowException if the file cannot be read or contains corrupted data
     */

    public ArrayList<Task> load() throws MeowException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            throw new MeowException("Meow! I couldn't load your saved tasks.");
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Creates the data directory if it does not exist.
     *
     * @param tasks the list of tasks to save
     * @throws MeowException if the file cannot be written
     */

    public void save(ArrayList<Task> tasks) throws MeowException {
        try {
            Files.createDirectories(filePath.getParent());

            try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                for (Task t : tasks) {
                    bw.write(encodeTask(t));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new MeowException("Meow! I couldn't save your tasks.");
        }
    }

    /**
     * Parses a single encoded task line from the storage file into a Task object.
     * Supports ToDo, Deadline, and Event task types.
     *
     * @param line the encoded task line to parse
     * @return the parsed Task object
     * @throws MeowException if the line is corrupted or contains an unknown task type
     */

    private Task parseTask(String line) throws MeowException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new MeowException("Meow! Corrupted save line: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        switch (type) {
        case "T":
            task = new ToDo(parts[2]);
            break;
        case "D":
            if (parts.length < 4) {
                throw new MeowException("Meow! "
                        + "Corrupted deadline line: " + line);
            }

            LocalDate by = LocalDate.parse(parts[3]);
            task = new Deadline(parts[2], by);
            break;
        case "E":
            if (parts.length < 5) {
                throw new MeowException("Meow! "
                        + "Corrupted event line: " + line);
            }

            LocalDate start = LocalDate.parse(parts[3].trim());
            LocalDate end = LocalDate.parse(parts[4].trim());
            task = new Event(parts[2], start, end);
            break;
        default:
            throw new MeowException("Meow! Unknown task type in save file: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Encodes a Task object into a string format for storage.
     * Format: `<type> | <isDone> | <description> | <optional dates>`
     *
     * @param t the task to encode
     * @return the encoded task string
     * @throws MeowException if the task type is unknown
     */

    private String encodeTask(Task t) throws MeowException {
        String doneFlag = (t.isDone()) ? "1" : "0";

        if (t instanceof ToDo) {
            return "T | " + doneFlag + " | " + t.getDescription();
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D | " + doneFlag + " | " + d.getDescription() + " | " + d.getBy().toString();
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E | " + doneFlag + " | " + e.getDescription()
                    + " | " + e.getStart().toString() + " | " + e.getEnd().toString();
        }

        throw new MeowException("Meow! Unknown task type: " + t.getClass().getName());
    }
}

