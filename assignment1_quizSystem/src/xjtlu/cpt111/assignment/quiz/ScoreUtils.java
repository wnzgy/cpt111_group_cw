package xjtlu.cpt111.assignment.quiz;

// 文件名：ScoreUtils.java
import java.io.*;
import java.util.*;


public class ScoreUtils {
    private static final String SCORE_FILE = "resources/score.txt";

    // 保存用户分数到文件
    public static void saveScores(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件加载用户分数
    public static List<User> loadScores() {
        List<User> users = new ArrayList<>();
        File scoreFile = new File(SCORE_FILE);

        // 如果文件不存在，则创建空文件
        if (!scoreFile.exists()) {
            try {
                scoreFile.getParentFile().mkdirs(); // 创建父目录
                scoreFile.createNewFile(); // 创建空的 score.txt 文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 如果文件存在，加载数据
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[0], "", Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}


