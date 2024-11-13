package xjtlu.cpt111.assignment.quiz;

// 文件名：Option.java
// 文件名：Option.java
public class Option {
    private String answer;
    private boolean isCorrect;

    // 构造函数
    public Option(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return answer;
    }
}
