import java.util.ArrayList;


public class Meow {
    private ArrayList<String> tasks;
    private boolean isActive;

    public Meow() {
        this.tasks = new ArrayList<>();
        this.isActive = true;
    }

    public void exit() {
        this.isActive = false;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }
}
