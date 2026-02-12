package meow.task;

import meow.util.DateTimeUtil;

import java.time.LocalDate;

public class Deadline extends Task {

    protected LocalDate byDate;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.byDate = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatDate(byDate) + ")";
    }

    public LocalDate getBy() {
        return this.byDate;
    }
}

