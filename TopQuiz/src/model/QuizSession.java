package model;

import central.QuizController.Topic;

/**
 * 
 * QuizSession.java
 * 
 * @author swabhat This class encapsulates the data that help keep track of
 *         scores in each subject. it contains the following member variables
 *         <ul>
 *         <li>topic name
 *         <li>number of questions answered correctly
 *         <li>number of questions answered incorrectly
 *         <li>total number of questions asked
 *         </ul>
 */
public class QuizSession {

	private Topic topicName;
	private int numRightAnswers, numWrongAnswers, numTotalQuestions, topicScore;

	public QuizSession() {

	}

	public QuizSession(Topic topicName) {
		this.topicName = topicName;
		numRightAnswers = 0;
		numWrongAnswers = 0;
		numTotalQuestions = 0;
		topicScore = 0;
	}

	public Topic getTopicName() {
		return topicName;
	}

	public void setTopicName(Topic topicName) {
		this.topicName = topicName;
	}

	public int getNumRightAnswers() {
		return numRightAnswers;
	}

	public void setNumRightAnswers(int numRightAnswers) {
		this.numRightAnswers = numRightAnswers;
	}

	public int getNumWrongAnswers() {
		return numWrongAnswers;
	}

	public void setNumWrongAnswers(int numWrongAnswers) {
		this.numWrongAnswers = numWrongAnswers;
	}

	public int getNumTotalQuestions() {
		return numTotalQuestions;
	}

	public void setNumTotalQuestions(int numTotalQuestions) {
		this.numTotalQuestions = numTotalQuestions;
	}

	/**
	 * @return the totalScore
	 */
	public int getTopicScore() {
		return topicScore;
	}

	/**
	 * @param totalScore
	 *            the totalScore to set
	 */
	public void setTopicScore(int totalScore) {
		this.topicScore = totalScore;
	}

}
