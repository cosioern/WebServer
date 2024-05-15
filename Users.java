import java.time.LocalTime;

/**
 * Class Users defines the information and actions associated with site user.
 * The class also stores usernames and passwords in implemented data structures.
 */
public class Users {

	// 
	private String username;
	private String password;
	private String email;
	private String name;
	private final LocalTime dateOfCreation;
	// private static users;
	// private hashmap passwords;

	public Users() {
		

	}

	// private String encrypter(String s) {}

	// getter methods
	public String getUsername() { return username; }
	public String getName() { return name; }
	public String email() { return email; }
	public String getDate() { return dateOfCreation.toString(); }

}
