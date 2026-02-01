package meow;

import java.util.ArrayList;

import meow.exception.MeowException;
import meow.task.Task;

/**
 * Task manager for the MeowBot application.
 * Manages the collection of tasks and the application's active state.
 */

public class Meow {
    private ArrayList<Task> tasks;
    private boolean isActive;

    /**
     * Constructs a new Meow instance with an empty task list and active state.
     */

    public Meow() {
        this.tasks = new ArrayList<>();
        this.isActive = true;
    }

    /**
     * Checks if the application is currently active.
     *
     * @return true if the application is active, false otherwise
     */

    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Marks the application as inactive, signaling it should exit.
     */

    public void exit() {
        this.isActive = false;
    }


    /**
     * Retrieves the current list of tasks.
     *
     * @return an ArrayList containing all tasks
     */

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add
     */

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Replaces the current task list with a new list of tasks.
     *
     * @param loaded the new list of tasks to set
     */

    public void setTasks(ArrayList<Task> loaded) {
        this.tasks = loaded;
    }

    /**
     * Deletes a task from the task list by its one-based index.
     *
     * @param oneBasedIndex the one-based index of the task to delete
     * @return the deleted task
     * @throws MeowException if the task number doesn't exist
     */

    public Task deleteTask(int oneBasedIndex) throws MeowException {
        int index = oneBasedIndex - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new MeowException("That task number doesn't exist.");
        }

        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list by its one-based index.
     *
     * @param oneBasedIndex the one-based index of the task to retrieve
     * @return the task at the specified index
     * @throws MeowException if the task number doesn't exist
     */

    public Task getTask(int oneBasedIndex) throws MeowException {
        int index = oneBasedIndex - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new MeowException("That task number doesn't exist.");
        }
        return tasks.get(index);
    }
}
