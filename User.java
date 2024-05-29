import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class Users defines the information and actions associated with site user.
 * The class also stores usernames and passwords in implemented data structures.
 * 
 * Ideas to implement: limit on login attempts per hour OR day
 */
public class User {

    /** User's name */
	private String name;
	
	/** User's unique user-name */
	private String username;
	
	/** User's email */
	private String email;
	
	/** Date the account was created */
    private LocalDate date;
	
	/** Hashed password */
    private String password;
	
    /** Salt to increase uniqueness of password. */
	private String salt;
	
	/** Tracks if user is logged in */
	private boolean isLoggedIn;
	
	/** Securely generate random bytes for hash */
	private static final SecureRandom rand = new SecureRandom();

	/**
	 * Initialize user's fields. Ensure unique user-name. Hash password.
	 * 
	 * @param name     is user's name.
	 * @param username is a unique String identifier for user.
	 * @param email    is the email used to communicate with user.
	 * @param password is a salted and hashed String used for authentication.
	 */
	public User(String name, String username, String email, String password) {
	    this.name = name;
	    this.username = username;
	    this.email = email;
	    this.date = LocalDate.now();
	    this.salt = generateSalt();
	    this.password = hash(password, this.salt);
	}

	// getter methods
	public String getUsername() { return username; }
	public String getName() { return name; }
	public String getEmail() { return email; }
	public String getDate() { return date.toString(); }

	/**
	 * Generate salt (random value) to further strengthen the password.
	 * Random 16 bytes encoded into two chars.
	 * Adapted from ChatGPT.
	 * 
	 * @return salt
	 */
	private static String generateSalt() {
	  byte[] salt = new byte[16];
	  rand.nextBytes(salt);
	  return Base64.getEncoder().encodeToString(salt);
	}
	
	/**
	 * This method hashes the String provided as an argument. Password is salted.
	 * Adapted from "https://www.geeksforgeeks.org/sha-256-hash-in-java/"
	 * 
	 * @param password is the String to be hashed
	 * @return the hashed password
	 * @throws NoSuchAlgorithmException if algorithm SHA-256 is not available
	 */
	private String hash(String password, String salt) {
	  
	  String saltedPswrd = salt + password;
	  
	  // get MessageDigest object that implements digest algorithm SHA-256
	  try {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	  
	  // digests String
      byte[] hashBytes = md.digest(saltedPswrd.getBytes());
      
      // reformats sign-magnitude representation of hash to a BigInteger
	  BigInteger bigIntHash = new BigInteger(1, hashBytes);
	  
	  // convert to hexadecimal String
	  String hashStr = bigIntHash.toString(16);
	  
	  // pad hashStr
	  while (hashStr.length() < 32) {
	    hashStr = "0" + hashStr;
	  }
	  
	  return hashStr;

	  } catch (NoSuchAlgorithmException e) { return null; }
      
	}
	
	/**
	 * Log in if user can be authenticated.
	 * 
	 * @param password
	 */
	private boolean logIn(String username, String password) {
	  
	  // hash argument password, compare to stored password
	  if (!this.password.equals(hash(password, this.salt)) || !this.username.equals(username) ) {
	    return false;
	  }
	  
	  this.isLoggedIn = true;
	  return true;
	  
	}
	
	/**
	 * Log out user unconditionally.
	 */
	private void logOut() {
	  isLoggedIn = false;
	}
	
	/**
	 * Ensure that username is unique. 
	 * @return
	 */
	private boolean uniqueUsername(String username) {
	 
	  
	  return false;
	}
	
}
