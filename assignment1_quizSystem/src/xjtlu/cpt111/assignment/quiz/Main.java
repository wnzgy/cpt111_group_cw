package xjtlu.cpt111.assignment.quiz;

import java.util.*;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static AuthService authService = new AuthService(users);  // 正确初始化 AuthService
    private static List<xjtlu.cpt111.assignment.quiz.model.Question> allQuestions = new ArrayList<>();  // 修改为 model 包中的 Question
    private static ScoreUtils scoreUtils = new ScoreUtils();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        boolean isLoggedIn = false;  // 用来判断是否已经登录
        User currentUser = null;  // 当前登录的用户

        // 加载用户数据
        authService.loadUsersFromCSV("resources/users.csv");  // 加载用户数据

        // 加载问题数据
        ReadQuestions.loadQuestions(allQuestions);  // 通过 ReadQuestions 加载问题
        // 保存用户和分数数据（初始化时）
        scoreUtils.saveScores(users);  // 在初始化时保存

        // 主菜单
        while (running) {
            if (isLoggedIn) {
                // 用户已登录时显示的选项
                System.out.println("1. Take Quiz");
                System.out.println("2. View Leaderboard");
                System.out.println("3. Logout");
                System.out.println("4. Exit");
                System.out.print("Please choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (option == 1) {
                    // 进行测验
                    takeQuiz(currentUser);
                    // 测验结束后保存分数
                    scoreUtils.saveScores(users);  // 测验结束时保存
                } else if (option == 2) {
                    // 排行榜
                    viewLeaderboard();
                } else if (option == 3) {
                    // 退出登录
                    System.out.println("Logging out...");
                    isLoggedIn = false;
                    currentUser = null;  // 清空当前用户
                } else if (option == 4) {
                    // 退出程序
                    running = false;
                } else {
                    System.out.println("Invalid option.");
                }
            } else {
                // 用户未登录时显示的选项
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Please choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (option == 1) {
                    // 注册
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (authService.register(userId, username, password)) {
                        System.out.println("Registration successful!");
                        // 注册成功后立即保存用户信息
                        scoreUtils.saveScores(users);  // 保存用户数据
                    } else {
                        System.out.println("Username already exists.");
                    }
                } else if (option == 2) {
                    // 登录
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    User user = authService.authenticate(loginUsername, loginPassword);
                    if (user != null) {
                        System.out.println("Login successful!");
                        isLoggedIn = true;  // 用户登录成功
                        currentUser = user;  // 设置当前用户
                        // 登录成功后保存用户数据
                        scoreUtils.saveScores(users); // 保存登录时的用户数据
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                } else if (option == 3) {
                    // 退出程序
                    running = false;
                } else {
                    System.out.println("Invalid option.");
                }
            }
        }
    }

    // 启动测验
    public static void takeQuiz(User user) {
        if (allQuestions.isEmpty()) {
            System.out.println("No questions available. Please load the questions first.");
            return;
        }
        Quiz quiz = new Quiz(allQuestions, user);
        quiz.startQuiz();
    }

    // 显示排行榜
    public static void viewLeaderboard() {
        System.out.println("Leaderboard:");
        Collections.sort(users, Comparator.comparingInt(User::getScore).reversed());
        for (User u : users) {
            System.out.println(u.getUsername() + ": " + u.getScore());
        }
    }
}
