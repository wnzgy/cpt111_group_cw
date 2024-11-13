// 文件名：User.java
package xjtlu.cpt111.assignment.quiz;
public class User {
    private String userId;
    private String username;
    private String password;
    private int score;

    // 构造函数 1：默认构造函数（仅接受id, username, password）
    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.score = 0; // 默认分数为0
    }

    // 构造函数 2：接受id, username, password, score
    public User(String userId, String username, String password, int score) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
}
