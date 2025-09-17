import java.io.Serializable;
import java.time.LocalDate;

public class Consult implements Serializable {
    private static final long serialVersionUID = 1L;
    private final LocalDate date;
    private final User user;
    private final String details;

    public Consult(LocalDate date, User user, String details) {
        this.date = date;
        this.user = user;
        this.details = details;
    }

    public String getAllDetails() {
        return date + " - @" + user.getRole() + " " + user.getUserName() + "\t| Notes: " + details;
    }
    public User getUser() {
        return user;
    }
}