import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.time.LocalDate;

public class Storage {
    private final Path filePath;

    public Storage() {
        this.filePath = Paths.get("data", "meow.txt");
    }

    public ArrayList<Task> load() throws MeowException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            throw new MeowException("Meow! I couldn't load your saved tasks.");
        }

        return tasks;
    }

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
                if (parts.length < 4) throw new MeowException("Meow! "
                        + "Corrupted deadline line: " + line);
                LocalDate by = LocalDate.parse(parts[3]);
                task = new Deadline(parts[2], by);
                break;
            case "E":
                if (parts.length < 5) throw new MeowException("Meow! "
                        + "Corrupted event line: " + line);
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

