import java.util.Scanner;

public class Meow {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("______________________________________");
        System.out.println("Hello! I'm Meow");
        System.out.println("What can I do for you?");
        System.out.println("______________________________________");

        while (true) {
            String input = sc.nextLine();
            System.out.println(input);
            System.out.println("______________________________________");

            if (input.equals("bye")) {
                System.out.println("______________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("______________________________________");
                break;
            }
        }
    }
}

