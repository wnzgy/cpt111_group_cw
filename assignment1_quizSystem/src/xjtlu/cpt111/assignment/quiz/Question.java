package xjtlu.cpt111.assignment.quiz;

import java.util.List;

// 文件名：Question.java
public class Question {
    private String topic;
    private String questionStatement;
    private List<Option> options;
    private String difficulty;

    // 构造函数
    public Question(String topic, String questionStatement, String difficulty, List<Option> options) {
        this.topic = topic;
        this.questionStatement = questionStatement;
        this.difficulty = difficulty;
        this.options = options;
    }

    public String getTopic() {
        return topic;
    }

    public String getQuestionStatement() {
        return questionStatement;
    }

    public List<Option> getOptions() {
        return options;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return "Question{" +
                "topic='" + topic + '\'' +
                ", questionStatement='" + questionStatement + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
