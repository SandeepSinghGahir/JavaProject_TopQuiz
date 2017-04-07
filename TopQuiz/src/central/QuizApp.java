package central;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import central.QuizController.Topic;
import guiComponents.MainCollectionPanel;
import model.QuestionsAndAnswers;
import model.QuizSession;

public class QuizApp {

	private JFrame frame;
	private MainCollectionPanel mainCollectionPanel;
	private QuizController quizController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizApp window = new QuizApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuizApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Top Quiz");
		// frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel titlePanel = new JPanel();
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);

		ImageIcon icon = createImageIcon("/resources/Q.gif");
		JLabel topQuizLabel = new JLabel("<html><h1>Top Quiz</h1></html>", icon, JLabel.RIGHT);
		titlePanel.add(topQuizLabel);

		mainCollectionPanel = new MainCollectionPanel();
		mainCollectionPanel.setParentWindow(this);
		frame.getContentPane().add(mainCollectionPanel, BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(600, 425));
		frame.pack();

	}

	public QuizController getQuizController() {
		return quizController;
	}

	public void setQuizController(QuizController quizController) {
		this.quizController = quizController;
	}

	public void startQuiz() {
		quizController = new QuizController();
		quizController.startQuiz();
		displayTopics();
	}

	public void displayTopics() {
		mainCollectionPanel.setStartCardPanel();
	}

	public void getQuestion(String subject) {
		QuestionsAndAnswers questionRecord = quizController.getQuestionDisplay(subject);
		if ("NO_MORE".equalsIgnoreCase(questionRecord.getQuestion())) {
			// getCongratsCardPanel();
			mainCollectionPanel.getCongratsPanel();
		} else {
			// displayQuestionRecord(questionBank);
			mainCollectionPanel.displayQuestionRecord(questionRecord);
		}

	}

	public void checkRadioButtonAnswer(String selectedButtonText) {
		quizController.checkRadioButtonQuestionAnswer(selectedButtonText);

	}

	public void checkCheckBoxAnswer(ArrayList<String> selectedOptions) {
		quizController.checkCheckBoxAnswer(selectedOptions);
	}

	public HashMap<Topic, QuizSession> getResult() {
		return (HashMap<Topic, QuizSession>) quizController.getResultMap();
	}

	public void checkAnswer() {
		quizController.checkAnswer();
	}

	public void checkOneWrdAnswer(String userTypedAnswer) {
		quizController.checkOneWrdAnswer(userTypedAnswer);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
