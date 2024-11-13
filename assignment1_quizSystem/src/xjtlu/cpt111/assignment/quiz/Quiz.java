package xjtlu.cpt111.assignment.quiz;

import java.util.*;

public class Quiz {
    private List<xjtlu.cpt111.assignment.quiz.model.Question> questions;
    private User user;
    private int score;

    public Quiz(List<xjtlu.cpt111.assignment.quiz.model.Question> allQuestions, User user) {
        this.user = user;
        this.score = 0;
        // 提供主题选择
        this.questions = selectTopicQuestions(allQuestions);
    }

    // 选择主题和题目
    private List<xjtlu.cpt111.assignment.quiz.model.Question> selectTopicQuestions(List<xjtlu.cpt111.assignment.quiz.model.Question> allQuestions) {
        Scanner scanner = new Scanner(System.in);
        Set<String> availableTopics = new HashSet<>();

        // 获取所有可用的主题
        for (xjtlu.cpt111.assignment.quiz.model.Question question : allQuestions) {
            availableTopics.add(question.getTopic());
        }

        // 显示可选的主题
        System.out.println("Available Topics:");
        int index = 1;
        Map<Integer, String> topicMap = new HashMap<>();
        for (String topic : availableTopics) {
            System.out.println(index + ". " + topic);
            topicMap.put(index++, topic);
        }

        // 获取用户选择的主题
        System.out.print("Select a topic (1-" + availableTopics.size() + "): ");
        int selectedOption = scanner.nextInt();
        String selectedTopic = topicMap.get(selectedOption);

        // 根据选择的主题过滤问题
        List<xjtlu.cpt111.assignment.quiz.model.Question> selectedQuestions = new ArrayList<>();

        for (xjtlu.cpt111.assignment.quiz.model.Question question : allQuestions) {
            if (question.getTopic().equals(selectedTopic)) {
                selectedQuestions.add(question);
            }
        }
        //难度题库
        List<xjtlu.cpt111.assignment.quiz.model.Question> easy_question = new ArrayList<>();
        List<xjtlu.cpt111.assignment.quiz.model.Question> medium_question = new ArrayList<>();
        List<xjtlu.cpt111.assignment.quiz.model.Question> hard_question = new ArrayList<>();
        List<xjtlu.cpt111.assignment.quiz.model.Question> veryhard_question = new ArrayList<>();
        List<xjtlu.cpt111.assignment.quiz.model.Question> final_question = new ArrayList<>();
        //每个难度的题目分为一组，并且如果选项只有一个，则为无效选项
        for(xjtlu.cpt111.assignment.quiz.model.Question question : selectedQuestions){
            if(question.getDifficulty().toString()=="EASY" && question.getOptions().length>1){
               easy_question.add(question);
            }else if(question.getDifficulty().toString()=="MEDIUM" && question.getOptions().length>1){
                medium_question.add(question);
            }else if(question.getDifficulty().toString()=="HARD" && question.getOptions().length>1){
                hard_question.add(question);
            }else if(question.getDifficulty().toString()=="VERY_HARD" && question.getOptions().length>1){
                veryhard_question.add(question);
            }
        }
        Random random = new Random();
//        System.out.println("easy:"+easy_question.size());
//        System.out.println("medium:"+medium_question.size());
//        System.out.println("hard:"+hard_question.size());
//        System.out.println("veryhard:"+veryhard_question.size());
        int easy_index = random.nextInt(easy_question.size());
        int medium_index = random.nextInt(medium_question.size());
        int hard_index = random.nextInt(hard_question.size());
        int veryhard_index = random.nextInt(veryhard_question.size());
        final_question.add(easy_question.get(easy_index));
        final_question.add(medium_question.get(medium_index));
        final_question.add(hard_question.get(hard_index));
        final_question.add(veryhard_question.get(veryhard_index));

        return final_question;
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        // 按照题目顺序展示问题
        for (xjtlu.cpt111.assignment.quiz.model.Question question : questions) {
            System.out.println("Question: " + question.getQuestionStatement()+" Difficulty: " + question.getDifficulty());

            // Convert the array of Option to List<Option>
            List<xjtlu.cpt111.assignment.quiz.model.Option> options = Arrays.asList(question.getOptions());

            // Shuffle options to randomize the order
            Collections.shuffle(options);

            // Print the options without showing correct answer
            for (int i = 0; i < options.size(); i++) {
                // Only print the option without any indication of whether it is correct
                System.out.println((i + 1) + ". " + options.get(i).getAnswer());
            }

            System.out.print("Select an option (1-" + options.size() + "): ");
            int answer = scanner.nextInt() - 1;  // Subtract 1 for zero-based indexing

            // Check if the selected answer is correct without showing correct answer
            if (options.get(answer).isCorrectAnswer()) {
                System.out.println("Your answer is correct!");
                score++;
            }else{
                System.out.println("Your answer is wrong,the correct answer is " + options.get(answer).getAnswer());
            }
        }

        // Update the user's score after the quiz
        user.updateScore(score);
        System.out.println("Quiz completed! Your score: " + score);
    }
}
