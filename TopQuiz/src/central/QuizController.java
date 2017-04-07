package central;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.QuestionsAndAnswers;
import model.QuizSession;

/**
 * QuizController.java
 * @author swabhat
 */

/**
 * This class controls the communication between the view and the model. All
 * data processing like checking answers, selecting a question take place
 * through this class
 */
public class QuizController {

	private ReadQuestions questionBank;
	private QuestionsAndAnswers questionRecord;
	private static AnswerStatus currentAnswerStatus;
	public static final String C = "C", JAVA = "Java", C_PLUS = "C++";
	private Map<Topic, QuizSession> resultMap;

	public enum Topic {
		C, C_PLUS, JAVA
	}

	private Topic currentTopic;

	enum AnswerStatus {
		RIGHT, WRONG, UNKNOWN
	}

	public enum QuestionType {
		TEXTUAL, AUDIO, VIDEO, IMAGE
	};

	public enum AnswerType {
		SINGLE, MULTIPLE, TEXTUAL
	}

	public static final String NO_MORE = "NO_MORE";// indicates that all
	// questions have been asked
	// (no more questions left
	// to be asked). Used in ReadQuestions.

	public QuizController() {

	}

	/**
	 * This method is invoked whenever the user clicks on "Next" button or
	 * selects a topic. It keeps track of the current topic to help keep track
	 * of the score.
	 * 
	 * @param String
	 *            Topic name or "Next" if called on click of next.
	 * @return QuestionsAndAnswers Question record to be displayed.
	 */
	public QuestionsAndAnswers getQuestionDisplay(String subject) {

		if (!subject.equalsIgnoreCase("Next")) {
			setCurrentTopic(subject);
			if (questionBank == null) {
				questionBank = new ReadQuestions(subject);
			}
		}
		return getNextQuestion(this.currentTopic);

	}

	/**
	 * This method is called on the click of the "start quiz" method. it
	 * initializes the QuizSession to keep track of score.
	 * 
	 */
	public void startQuiz() {
		resultMap = new HashMap<Topic, QuizSession>();
		for (Topic subject : Topic.values()) {
			QuizSession quizSession = new QuizSession(subject);
			resultMap.put(subject, quizSession);
		}
	}

	/*
	 * Helper method that fetches a unique question record.
	 * 
	 * @ param Topic topic of users choice on which he wishes to be asked a
	 * question
	 * 
	 * @return QuestionsAndAnswers A question record.
	 */
	private QuestionsAndAnswers getNextQuestion(Topic topic) {
		questionRecord = questionBank.getARecord(topic);
		return questionRecord;
	}

	/**
	 * It is called every Time a Radio button is selected and checks if its the
	 * right answer. Sets the current Answer status accordingly.
	 * 
	 * @param String
	 *            Option selected by the user
	 */
	public void checkRadioButtonQuestionAnswer(String selectedButtonText) {

		String answer = questionRecord.getAnswers().get(0);
		if (answer.equalsIgnoreCase(selectedButtonText)) {
			currentAnswerStatus = AnswerStatus.RIGHT;
		} else {
			currentAnswerStatus = AnswerStatus.WRONG;
		}
		//System.out.println("Answer is " + currentAnswerStatus);
	}

	/**
	 * It is called everytime a checkBox is selected and checks if its the right
	 * answer. Sets the current Answer status accordingly.
	 * 
	 * @param String
	 *            Options selected by the user
	 */

	public void checkCheckBoxAnswer(ArrayList<String> selectedAnswer) {

		int correctNumAns = questionRecord.getAnswers().size();
		ArrayList<String> actualAnswers = (ArrayList<String>) questionRecord.getAnswers();
		if (correctNumAns == selectedAnswer.size()) {

			for (int i = 0; i < selectedAnswer.size(); i++) {
				boolean selectedAnswerIsRight = false;
				String answerChosen = selectedAnswer.get(i);
				if (answerChosen.equalsIgnoreCase(actualAnswers.get(i))) {
					selectedAnswerIsRight = true;
				} else {
					selectedAnswerIsRight = false;
				}
				if (selectedAnswerIsRight) {
					currentAnswerStatus = AnswerStatus.RIGHT;
				} else {
					currentAnswerStatus = AnswerStatus.WRONG;
				}
			}
		} else if (selectedAnswer.size() > 0) {
			currentAnswerStatus = AnswerStatus.WRONG;
		} else {
			currentAnswerStatus = AnswerStatus.UNKNOWN;
		}
		//System.out.println("Answer is " + currentAnswerStatus);

	}

	/**
	 * This method is called whenever the user clicks on the "Next" or "Submit"
	 * button. It checks the Answer status (Right/Wrong) and increments the
	 * score accordingly.
	 */
	public void checkAnswer() {
		if (questionRecord != null) { // Do not check for answer the very first
										// time when no question is displayed
			QuizSession quizSession = resultMap.get(currentTopic);
			if (currentAnswerStatus != null) {
				switch (currentAnswerStatus) {

				case RIGHT:
					int numRightAns = quizSession.getNumRightAnswers() + 1;
					quizSession.setNumRightAnswers(numRightAns);
					int weight = questionRecord.getWeightage();
					int score = quizSession.getTopicScore() + weight;
					quizSession.setTopicScore(score);
					break;

				case WRONG:
					int numWrongAns = quizSession.getNumWrongAnswers() + 1;
					quizSession.setNumWrongAnswers(numWrongAns);
					break;
				}

			}
			int numQuestions = quizSession.getNumTotalQuestions() + 1;
			quizSession.setNumTotalQuestions(numQuestions);
			// set currentAnswerStatus to unknown for the next question
			currentAnswerStatus = AnswerStatus.UNKNOWN;
		}
	}

	/*
	 * sets the current topic to help keep track of question asked and the score
	 * in each topic separately.
	 */
	private void setCurrentTopic(String subject) {

		switch (subject) {

		case C:
			currentTopic = Topic.C;
			break;

		case C_PLUS:
			currentTopic = Topic.C_PLUS;
			break;

		case JAVA:
			currentTopic = Topic.JAVA;
			break;
		}
	}

	/**
	 * 
	 * @return Map Returns a map of topic name and the related statistics that
	 *         are needed to plot a graph
	 */
	public Map<Topic, QuizSession> getResultMap() {
		return resultMap;
	}

	/**
	 * sets the topic name and the related statistics that are needed to plot a
	 * graph into a hash Map
	 * 
	 */
	public void setResultMap(Map<Topic, QuizSession> resultMap) {
		this.resultMap = resultMap;
	}

	/**
	 * It is called every time a answer is typed in the text field and checks if
	 * its the right answer. Sets the current Answer status accordingly.
	 * 
	 * @param String
	 *            Options selected by the user
	 */
	public void checkOneWrdAnswer(String userTypedAnswer) {
		String correctAnswer = questionRecord.getAnswers().get(0);// its one wrd
																	// answer
		if (correctAnswer.equalsIgnoreCase(userTypedAnswer.trim())) {
			currentAnswerStatus = AnswerStatus.RIGHT;
		} else {
			currentAnswerStatus = AnswerStatus.WRONG;
		}

	}
}
