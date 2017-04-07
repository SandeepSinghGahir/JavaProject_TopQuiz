package central;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import central.QuizController.Topic;
import model.QuestionBank;
import model.QuestionsAndAnswers;

/**
 * 
 * ReadQuestions.java
 * 
 * @author swabhat This class reads a JSON file that contains all the questions
 *         for the quiz.
 * @exception IOException
 *                while reading the file
 * @see IOException
 *
 */
public class ReadQuestions {
	private QuestionsAndAnswers questionAndAnswerRecord;
	private List<QuestionsAndAnswers> questionArray;
	private static Set<Integer> askedQuestions = new HashSet<Integer>();;
	private QuestionBank Java;
	private QuestionBank CPlusPlus;
	private QuestionBank C;

	public ReadQuestions(String subject) {
		JSONParser parser = new JSONParser();
		questionArray = new ArrayList<QuestionsAndAnswers>();
		String jsonData = "";
		String line = null;
		String filePath = (new File("")).getAbsolutePath();
		String fileName = filePath + "/resources/QuestionBank.json";// "/src/resources/QuestionBank.json";
		try {
			/*
			 * Read the contents of .json file which has the Question Bank in
			 * the form of JSON object.
			 */

			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer dataFromFile = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				dataFromFile.append(line);
			}
			bufferedReader.close();

			jsonData = dataFromFile.toString();
			Object obj = parser.parse(jsonData);

			JSONObject jsonObject = (JSONObject) obj;
			JSONArray languages = (JSONArray) jsonObject.get("Questions");
			int numLanguages = languages.size();

			for (int i = 0; i < numLanguages; i++) {
				JSONObject qBank = (JSONObject) languages.get(i);
				String subjectName = (String) qBank.get("subject");
				JSONArray subjectQuestions = (JSONArray) qBank.get("subjectDetail");
				ArrayList<QuestionsAndAnswers> questionArray = new ArrayList<QuestionsAndAnswers>();
				int numOfQuestions = subjectQuestions.size();
				for (int i1 = 0; i1 < numOfQuestions; i1++) {
					JSONObject questionDetailsJsonObject = (JSONObject) subjectQuestions.get(i1);
					questionAndAnswerRecord = new QuestionsAndAnswers(questionDetailsJsonObject);
					questionArray.add(questionAndAnswerRecord);
				}
				switch (subjectName) {
				case "java":
					Java = new QuestionBank(Topic.JAVA, questionArray);
					break;
				case "c":
					C = new QuestionBank(Topic.C, questionArray);
					break;
				case "c++":
					CPlusPlus = new QuestionBank(Topic.C_PLUS, questionArray);
					break;
				}
			}

		} catch (IOException e) {
			System.out.println("Data file : '" + fileName + "' was not found");
			e.printStackTrace();
			// System.out.println("Please place the resource folder in the same
			// folder as the jar file.");
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Unable to read .json file");
			e.printStackTrace();
		}

	}

	/**
	 * Randomly select a question from the QuestionBank and keep track of
	 * already asked questions. If a question has been asked then it randomly
	 * picks another question.
	 */

	public QuestionsAndAnswers getARecord(Topic topic) {
		switch (topic) {
		case JAVA:
			return Java.GetRecord();
		case C:
			return C.GetRecord();
		case C_PLUS:
			return CPlusPlus.GetRecord();
		default:
			int random = new Random().nextInt() % 3;
			return random == 0 ? Java.GetRecord() : random == 1 ? C.GetRecord() : CPlusPlus.GetRecord();
		}
	}

	/*
	 * public String getAQuestion(){
	 * 
	 * questionAndAnswerRecord = new QuestionBankTemp().getARecord(); String
	 * question = questionAndAnswerRecord.getQuestion(); return question;
	 * 
	 * }
	 */

}
