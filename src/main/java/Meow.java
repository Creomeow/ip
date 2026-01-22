import java.util.ArrayList;

public class Meow {
    private ArrayList<String> tasks;
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

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
    }
}
