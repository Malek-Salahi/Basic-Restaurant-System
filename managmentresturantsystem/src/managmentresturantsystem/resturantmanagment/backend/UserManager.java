/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managmentresturantsystem.resturantmanagment.backend;

/**
 *
 * @author hp
 */
import java.io.*;
import java.util.HashMap;

public class UserManager {
    private HashMap<String, User> users = new HashMap<>();
    private final String filePath = "D:\\managmentresturantsystem\\src\\managmentresturantsystem\\resturantmanagment\\backend\\users.txt";

    public UserManager() {
        loadUsersFromFile();
    }

    // adding new user
    public void addUser(String username, String password, String role) {
        User user = new User(username, password, role);
        users.put(username, user);
        saveUserToFile(user);
    }

    // checking login
public String login(String username, String password) {
    User user = users.get(username);
    if (user != null) {
        if (user.getPassword().equals(password)) {
            return "Login successful! Welcome, " + user.getUsername(); // Success message
        } else {
            return "Invalid password."; // Error message for incorrect password
        }
    } else {
        return "Invalid username."; // Error message for invalid username
    }
}


    // checking permissions
    public boolean hasPermission(String username, String requiredRole) {
        User user = users.get(username);
        return user != null && user.getRole().equals(requiredRole);
    }

    // saving user to file
    private void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }public User getUserByUsername(String username) {
    return users.get(username);  // Return the User object based on the username
}


    // loading users from file
    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    users.put(user.getUsername(), user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }
}