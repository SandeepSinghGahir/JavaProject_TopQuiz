package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import central.QuizController;
import central.QuizController.Topic;

/**
 * 
 * QuestionBank.java
 * 
 * @author swabhat This class contains the topics and the related questions.
 */
public class QuestionBank {
	private Topic language;
	private ArrayList<QuestionsAndAnswers> qas;
	private HashSet<Integer> askedQuestions;

	public QuestionBank(Topic name, ArrayList<QuestionsAndAnswers> qas) {
		this.language = name;
		this.qas = qas;
		this.askedQuestions = new HashSet<Integer>();
	}

	/**
	 * Randomly selects a question from the question bank and maintains a list
	 * of questions that have already been asked so that questions are not
	 * repeated @ return QuestionsAndAnswers A single question record.
	 */
	public QuestionsAndAnswers GetRecord() {
		int totalNumberOfQuestions = qas.size();
		int index = 0;

		if (askedQuestions.size() == totalNumberOfQuestions) {
			return new QuestionsAndAnswers(QuizController.NO_MORE);
		}

		do {
			index = new Random().nextInt(totalNumberOfQuestions);
		} while (askedQuestions.contains(index));

		askedQuestions.add(index);
		return qas.get(index);
	}

}
