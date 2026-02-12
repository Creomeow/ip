package meow.task;

import meow.util.DateTimeUtil;

import java.time.LocalDate;

public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String description, LocalDate startDate, LocalDate endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + DateTimeUtil.formatDate(startDate) + " to: " + DateTimeUtil.formatDate(endDate) + ")";
    }

    public LocalDate getStart() {
        return this.startDate;
    }

    public LocalDate getEnd() {
        return this.endDate;
    }
}
