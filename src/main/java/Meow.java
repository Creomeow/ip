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
}
