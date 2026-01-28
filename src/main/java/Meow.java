import java.util.ArrayList;

public class Meow {
    private ArrayList<Task> tasks;
    private boolean isActive;

    public Meow() {
        this.tasks = new ArrayList<>();
        this.isActive = true;
    }

    public boolean isActive(){
        return this.isActive;
    }

    public void exit() {
        this.isActive = false;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void setTasks(ArrayList<Task> loaded) {
        this.tasks = loaded;
    }

    public Task deleteTask(int oneBasedIndex) throws MeowException {
        int index = oneBasedIndex - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new MeowException("Meow! That task number doesn't exist.");
        }

        return tasks.remove(index);
    }
}
