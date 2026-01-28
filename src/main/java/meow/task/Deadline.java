package meow.task;

import meow.util.DateTimeUtil;

import java.time.LocalDate;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatDate(by) + ")";
    }

    public LocalDate getBy() {
        return this.by;
    }
}

