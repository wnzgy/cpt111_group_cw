package xjtlu.cpt111.assignment.quiz;

import xjtlu.cpt111.assignment.quiz.model.Question;
import xjtlu.cpt111.assignment.quiz.util.IOUtilities;

import java.util.List;
import java.util.ArrayList;  // 导入 ArrayList 类

public class ReadQuestions {

	private static final String RESOURCES_PATH = "src/main/resources/";
	private static final String QUESTIONS_BANK_PATH = "resources/questionsBank/";

	// 加载问题并将其添加到传入的 allQuestions 列表中
	public static void loadQuestions(List<Question> allQuestions) {
		String filename = QUESTIONS_BANK_PATH; // 使用实际路径加载问题

		try {
			System.out.println("===\n=== read questions - started\n===");
			// 调用 IOUtilities 来读取问题
			Question[] questions = IOUtilities.readQuestions(filename);

			// 检查问题是否为空，若不为空，添加到 allQuestions 列表
			if (questions != null && questions.length > 0) {
				for (Question question : questions) {
					allQuestions.add(question); // 将问题添加到 allQuestions 列表中
					System.out.println("Loaded question: " + question);  // 输出加载的每个问题
				}
			} else {
				System.out.println("No questions found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("===\n=== read questions - complete\n===");
		}
	}

	// main 方法测试加载问题
	public static void main(String... arguments) {
		// 创建一个空的 List 来存储问题
		List<Question> allQuestions = new ArrayList<>();
		loadQuestions(allQuestions);  // 调用方法加载问题

		// 显示已加载的所有问题
		if (allQuestions.isEmpty()) {
			System.out.println("No questions loaded.");
		} else {
			System.out.println("Loaded questions:");
			for (Question question : allQuestions) {
				System.out.println(question);
			}
		}
	}
}
