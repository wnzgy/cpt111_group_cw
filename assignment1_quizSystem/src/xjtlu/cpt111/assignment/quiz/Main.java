package xjtlu.cpt111.assignment.quiz;

import java.util.*;

public class Main {
    // List to store all the users
    private static List<User> users = new ArrayList<>();
    // Initialize AuthService with the users list to manage authentication
    private static AuthService authService = new AuthService(users);
    // List to store all the quiz questions
    private static List<xjtlu.cpt111.assignment.quiz.model.Question> allQuestions = new ArrayList<>();
    // Utility class for handling score saving and leaderboard viewing
    private static ScoreUtils scoreUtils = new ScoreUtils();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        boolean isLoggedIn = false;  // Flag to check if the user is logged in
        User currentUser = null;  // The currently logged-in user

        // Load the user data from the CSV file
        authService.loadUsersFromCSV("resources/users.csv");

        // Load all the quiz questions
        ReadQuestions.loadQuestions(allQuestions);

        // Save the scores at the beginning
        scoreUtils.saveScores(users);

        // Main menu loop
        while (running) {
            if (isLoggedIn) {
                // Display options if the user is logged in
                System.out.println("\nWelcome, " + currentUser.getUsername() + "!");
                System.out.println("1. Take Quiz");
                System.out.println("2. View Leaderboard");
                System.out.println("3. View Dashboard");
                System.out.println("4. Logout");
                System.out.println("5. Exit");
                System.out.print("Please choose an option: ");

                try {
                    // Get user input for the chosen option
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    if (option == 1) {
                        // Start the quiz if option 1 is selected
                        takeQuiz(currentUser);
                        // After the quiz ends, save the scores
                        scoreUtils.saveScores(users);
                    } else if (option == 2) {
                        // View the leaderboard if option 2 is selected
                        scoreUtils.view_leaderboard();
                    } else if (option == 3) {
                        // View the user's dashboard if option 3 is selected
                        currentUser.viewDashboard();
                    } else if (option == 4) {
                        // Logout if option 4 is selected
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                        currentUser = null;  // Clear the current user
                    } else if (option == 5) {
                        // Exit the application if option 5 is selected
                        running = false;
                    } else {
                        // If an invalid option is selected
                        System.out.println("Invalid option.");
                    }
                } catch (InputMismatchException e) {
                    // Handle invalid input
                    System.out.println("Invalid input, please try again.");
                    scanner.nextLine();  // Clear the input buffer
                }
            } else {
                // Display options if the user is not logged in
                System.out.println("\nWelcome to the Quiz System!");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Please choose an option: ");

                try {
                    // Get user input for the chosen option
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    if (option == 1) {
                        // Register a new user if option 1 is selected
                        System.out.print("Enter user ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        if (authService.register(userId, username, password, "resources/users.csv")) {
                            System.out.println("Registration successful!");
                            // Save the newly registered user data
                            scoreUtils.saveScores(users);
                        } else {
                            System.out.println("Username already exists.");
                        }
                    } else if (option == 2) {
                        // Login if option 2 is selected
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        User user = authService.authenticate(loginUsername, loginPassword);
                        if (user != null) {
                            System.out.println("Login successful!");
                            isLoggedIn = true;  // Set the login flag to true
                            currentUser = user;  // Set the current user
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                    } else if (option == 3) {
                        // Exit the application if option 3 is selected
                        running = false;
                    } else {
                        // If an invalid option is selected
                        System.out.println("Invalid option.");
                    }
                } catch (InputMismatchException e) {
                    // Handle invalid input
                    System.out.println("Invalid input, please try again.");
                    scanner.nextLine();  // Clear the input buffer
                }
            }
        }
    }

    // Start the quiz for the logged-in user
    public static void takeQuiz(User user) {
        if (allQuestions.isEmpty()) {
            System.out.println("No questions available. Please load the questions first.");
            return;
        }
        Quiz quiz = new Quiz(allQuestions, user);
        quiz.startQuiz();  // Start the quiz
    }
}
