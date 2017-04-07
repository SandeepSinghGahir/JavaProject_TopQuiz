package model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import central.QuizController.AnswerType;
import central.QuizController.QuestionType;

/**
 * QuestionsAndAnswers.java
 * @author swabhat
 */

/**
 * This class encapsultes all the information related to a quiz question. This
 * state information includes
 * <ul>
 * <li>The problem question
 * <li>The answer to the question
 * <li>The question and answer type. For e.g, multiple choice or One word answer
 * <li>The points/weightage for each question
 * <li>Depending on the question type it will hold, the options for the answer
 * if its a multi choice question. Else it will hold the file name containing
 * the question.
 * </ul>
 */
public class QuestionsAndAnswers {

	private String question;

	public QuestionType questionType;
	public AnswerType answerType;
	private List<String> options = new ArrayList<String>();
	private List<String> answers;
	private int weightage;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * @return the weightage
	 */
	public int getWeightage() {
		return weightage;
	}

	/**
	 * @param weightage
	 *            the weightage to set
	 */
	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}

	public QuestionsAndAnswers(String question) {
		this.question = question;
	}
	/*
	 * public QuestionsAndAnswers(String question, ArrayList<String> options){
	 * this(question); this.options = options; }
	 * 
	 * public QuestionsAndAnswers(String question, ArrayList<String> options,
	 * ArrayList<String> answers){ this(question, options); this.answers =
	 * answers;
	 * 
	 * }
	 */

	/*
	 * Parses the json object and sets the attribute values.
	 * 
	 * @param JSONObject The object that contains the necessary problem question
	 * details
	 */
	public QuestionsAndAnswers(JSONObject obj) {
		answers = new ArrayList<String>();
		options = new ArrayList<String>();
		this.question = (String) obj.get("question");

		JSONArray answerArray = (JSONArray) obj.get("answer");
		int numOfAns = answerArray.size();
		for (int i = 0; i < numOfAns; i++) {
			String answers = (String) answerArray.get(i);
			this.answers.add(answers);
		}

		JSONArray optionsArray = (JSONArray) obj.get("options");
		int numOfOptions = optionsArray.size();
		for (int i = 0; i < numOfOptions; i++) {
			String optionText = (String) optionsArray.get(i);
			this.options.add(optionText);
		}

		String answerKind = (String) obj.get("answerType");
		String questionKind = (String) obj.get("questionType");
		this.answerType = findAnswerType(answerKind);
		this.questionType = findQuestionType(questionKind);
		String scoreWeight = (String) obj.get("weightage") != "" ? (String) obj.get("weightage") : "1";
		this.setWeightage(Integer.parseInt(scoreWeight));
	}

	/*
	 * Helper method that parses the the string "answer type" value into one of
	 * the enum Answer types.
	 */
	private AnswerType findAnswerType(String answerKind) {
		AnswerType answerType = AnswerType.SINGLE;
		if (answerKind.equalsIgnoreCase("Multiple")) {
			answerType = AnswerType.MULTIPLE;
		} else if (answerKind.equalsIgnoreCase("Textual")) {
			answerType = AnswerType.TEXTUAL;
		}

		return answerType;
	}

	/*
	 * Helper method that parses the the string "question type" value into one
	 * of the enum question types.
	 */
	private QuestionType findQuestionType(String questionKind) {
		QuestionType questionType = QuestionType.TEXTUAL;
		if (questionKind.equalsIgnoreCase("image")) {
			questionType = QuestionType.IMAGE;
		}
		return questionType;
	}

	/**
	 * Helps in printing the contents of the question record
	 * 
	 * @return String : attributes of the question record
	 */
	public String toString() {

		StringBuffer s = new StringBuffer();
		s.append("Question :" + this.question);
		s.append("\n");
		s.append("Options: ");
		for (int i = 0; i < this.options.size(); i++) {
			s.append("\n\t");
			s.append(this.options.get(i));
		}
		s.append("\n");
		s.append("Answer: ");
		for (int i = 0; i < this.answers.size(); i++) {
			s.append("\n\t");
			s.append(this.answers.get(i));
		}
		return s.toString();

	}
}
