package guiComponents;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import central.QuizApp;
import central.QuizController.Topic;
import model.QuestionsAndAnswers;
import model.QuizSession;

public class MainCollectionPanel extends JPanel {

	private WelcomeCardPanel welcomePanel;
	private QuizApp parentWindow;
	private ResultCardPanel congratsCardPanel;
	private CentralQuizCardPanel centralQuizCardPanel;

	public MainCollectionPanel() {
		this.setLayout(new CardLayout(0, 0));
		welcomePanel = new WelcomeCardPanel();
		welcomePanel.setParentPanel(this);
		this.add(welcomePanel, "welcomePanel");
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, "welcomePanel");
	}

	public void startQuiz() {
		((QuizApp) parentWindow).startQuiz();
	}

	public void setParentWindow(QuizApp parent) {
		this.parentWindow = parent;
	}

	public QuizApp getParentWindow() {
		return this.parentWindow;
	}

	public void setStartCardPanel() {

		centralQuizCardPanel = new CentralQuizCardPanel();
		// setPanel(centralQuizCardPanel,"centralQuizCardPanel");
		centralQuizCardPanel.setParentPanel(this);
		this.add(centralQuizCardPanel, "centralQuizCardPanel");
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, "centralQuizCardPanel");
	}

	public void getQuestion(String subject) {
		parentWindow.getQuestion(subject);
	}

	public void setCongratsCardPanel(HashMap<Topic, QuizSession> quizResult) {
		congratsCardPanel = new ResultCardPanel(quizResult);
		// setPanel(congratsCardPanel, "congratsCardPanel");
		congratsCardPanel.setParentPanel(this);
		this.add(congratsCardPanel, "congratsCardPanel");
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, "congratsCardPanel");
	}

	public void displayQuestionRecord(QuestionsAndAnswers questionRecord) {
		centralQuizCardPanel.displayQuestionRecord(questionRecord);
		// setPanel(centralQuizCardPanel, "centralQuizCardPanel");
		centralQuizCardPanel.setParentPanel(this);
		this.add(centralQuizCardPanel, "centralQuizCardPanel");
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, "centralQuizCardPanel");
	}

	public void setPanel(JPanel panel, String contraint) {
		// panel.setParentPanel(this);
		this.add(panel, contraint);
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, panel.toString());
	}

	public void checkRadioButtonAnswer(String selectedButtonText) {
		parentWindow.checkRadioButtonAnswer(selectedButtonText);

	}

	public void checkCheckBoxAnswer(ArrayList<String> selectedOptions) {
		parentWindow.checkCheckBoxAnswer(selectedOptions);
	}

	public void getCongratsPanel() {

		HashMap<Topic, QuizSession> quizResult = parentWindow.getResult();
		setCongratsCardPanel(quizResult);

	}

	public void checkAnswer() {
		parentWindow.checkAnswer();
	}

	public void checkOneWrdAnswer(String userTypedAnswer) {
		parentWindow.checkOneWrdAnswer(userTypedAnswer);
	}
}
