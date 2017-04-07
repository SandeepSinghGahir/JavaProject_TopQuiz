package guiComponents;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.QuestionsAndAnswers;

/**
 * 
 * CentralQuizCardPanel.java
 * 
 * @author swabhat This class contains
 *         <ul>
 *         <li>A question panel, where the question is displyed.
 *         <li>A Side panel that displays the list of available topics
 *         <li>A buttons panel containing buttons to help navigate the quiz.
 *         </ul>
 */
public class CentralQuizCardPanel extends JPanel {
	private MainCollectionPanel parentPanel;
	private MainFrameSidePanel mainFrameSidePanel;
	private JPanel questionCollectionPanel;
	private BottomButtonPanel bottomButtonPanel;

	public CentralQuizCardPanel() {

		this.setLayout(new BorderLayout());

		mainFrameSidePanel = new MainFrameSidePanel();
		mainFrameSidePanel.setParentPanel(this);
		mainFrameSidePanel.setParentPanel(this);
		this.add(mainFrameSidePanel, BorderLayout.WEST);

		questionCollectionPanel = createStartingQuestionPanel();
		this.add(questionCollectionPanel, BorderLayout.CENTER);

		bottomButtonPanel = new BottomButtonPanel();
		bottomButtonPanel.setVisible(Boolean.FALSE);
		bottomButtonPanel.setParentPanel(this);
		this.add(bottomButtonPanel, BorderLayout.SOUTH);

	}

	/*
	 * Sets the parent panel. Creates child parent relation to facilitate
	 * communication between panels
	 */
	public void setParentPanel(MainCollectionPanel mainCollectionPanel) {
		this.parentPanel = mainCollectionPanel;
	}

	/*
	 * Gets parent panel.
	 */
	public MainCollectionPanel getParentPanel() {
		return parentPanel;
	}

	/*
	 * Passes the call to parent panel.
	 */
	public void getQuestion(String subject) {
		parentPanel.getQuestion(subject);
	}

	/*
	 * This method is invoked when the questin screen is to be displayed
	 * 
	 * @param QuestionsAndAnswers question to be displayed.
	 */
	public void displayQuestionRecord(QuestionsAndAnswers questionRecord) {
		if(questionCollectionPanel.getClass().equals(QuestionCollectionPanel.class))
		((QuestionCollectionPanel) questionCollectionPanel).getTimer().stop();
		this.remove(questionCollectionPanel);
		questionCollectionPanel = new QuestionCollectionPanel(questionRecord);
		((QuestionCollectionPanel) questionCollectionPanel).setParentPanel(this);
		this.add(questionCollectionPanel, BorderLayout.CENTER);
		bottomButtonPanel.setVisible(Boolean.TRUE);

	}

	/*
	 * Passes the call to parent panel.
	 */
	public void checkRadioButtonAnswer(String selectedButtonText) {
		parentPanel.checkRadioButtonAnswer(selectedButtonText);
	}

	/*
	 * Passes the call to parent panel.
	 */
	public void checkCheckBoxAnswer(ArrayList<String> selectedOptions) {
		parentPanel.checkCheckBoxAnswer(selectedOptions);
	}

	/*
	 * Passes the call to parent panel.
	 */
	public void showResult() {
		parentPanel.getCongratsPanel();
	}

	/*
	 * Passes the call to parent panel.
	 */
	public void checkAnswer() {
		parentPanel.checkAnswer();
	}

	/*
	 * Passes the call to parent panel.
	 */
	public void checkOneWrdAnswer(String userTypedAnswer) {
		parentPanel.checkOneWrdAnswer(userTypedAnswer);
	}

	/**
	 * This Panel appears after clciking on Start quiz.
	 */
	public JPanel createStartingQuestionPanel() {
		JPanel tempPanel = new JPanel();
		tempPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
		ImageIcon icon = createImageIcon("/resources/QBig.gif");
		// JLabel selectTopicLabel = new JLabel("<html><h2>Select a Topic to
		// begin</h2></html>");
		JLabel selectTopicLabel = new JLabel(icon);
		selectTopicLabel.setText("<html><h2>Select a Topic to begin</h2></html>");
		selectTopicLabel.setHorizontalTextPosition(JLabel.CENTER);
		selectTopicLabel.setVerticalTextPosition(JLabel.BOTTOM);
		tempPanel.add(selectTopicLabel);
		return tempPanel;
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
