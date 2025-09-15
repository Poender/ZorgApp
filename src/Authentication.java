import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    public ArrayList<User> userArray = new ArrayList<>();

    Authentication() {
        System.out.format("====| LOG IN |%s\n", "=".repeat(80));
    }

    public User logIn() {
        while (userArray.isEmpty()) {
            try {
                Path filePath = Paths.get("userData.db");
                long linesCount = Files.lines(filePath).count();
                for (int i = 1; i <= linesCount; i++) {
                    userArray.add(User.readUser(i));
                }
            } catch (Exception e) {
                System.out.println("ERROR reading from file");
            }
        }

        System.out.flush();
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String userName = sc.nextLine();
        if (userName.isEmpty()) {userName = "null";}

        System.out.print("Pin: ");
        while (!sc.hasNextInt()) {
            System.out.println("Pin can only be digits!");
            sc.nextLine();
            System.out.print("Pin: ");
        }
        int pin = sc.nextInt();
        for (User user : userArray) {
            if (user.getUserName().equals(userName.substring(0, 1).toUpperCase() + userName.substring(1)) & user.getPin() == pin) {
                System.out.format("SUCCES - Logged in as %s %s\n",user.getRole(), user.getUserName());
                return user;
            }
        }
        System.out.println("Invalid username or pin");
        return null;
    }
}
