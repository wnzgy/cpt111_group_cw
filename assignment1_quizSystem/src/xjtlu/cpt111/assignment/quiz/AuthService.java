package xjtlu.cpt111.assignment.quiz;

// 文件名：AuthService.java
// 文件名：AuthService.java
import java.util.List;
// 文件名：AuthService.java
import java.io.*;
import java.util.*;

// 文件名：AuthService.java
import java.io.*;
import java.util.*;

public class AuthService {
    private List<User> users;

    public AuthService(List<User> users) {
        this.users = users;
    }

    // 从CSV文件加载用户
    public void loadUsersFromCSV(String filePath) {
        File file = new File(filePath);
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String student = input.nextLine();
                String[] values = student.split(",");
                String userId = values[0];
                String username = values[1];
                String password = values[2]; // 读取密码为字符串类型
                System.out.println(userId+" "+username + " " + password);
                users.add(new User(userId, username, password)); // 将用户添加到列表
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // 注册方法
    public boolean register(String userId, String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // 用户名已存在
            }
        }
        users.add(new User(userId, username, password));
        return true;
    }

    // 登录方法
    public User authenticate(String username, String password) {
        for (User user : users) {
//            System.out.println("name:"+user.getUsername());
//            System.out.println("password:"+user.getPassword());
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // 登录失败
    }
}
