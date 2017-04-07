package guiComponents;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import central.QuizController.AnswerType;
import model.QuestionsAndAnswers;

public class QuestionCollectionPanel extends JPanel {

	private QuestionsAndAnswers questionRecord;
	private CentralQuizCardPanel parentPanel;
	JPanel questionPanel, optionsCollectionPanel, timerPanel;// radioButtonOptionsCardPanel,
	// checkBoxOptionsCardPanel;
	private JCheckBox[] optionsCheckBoxes;
	private JProgressBar progressBar;
	private Timer timer;
	private int progressValue;

	public QuestionCollectionPanel() {
		this.setLayout(new BorderLayout(0, 0));

	}

	/**
	 * Initialize the contents of questionDisplayPanel. it contains question
	 * Label (which is common for all kinds of questions). The options panel
	 * contains different card panels for displaying the different options for
	 * single choice questions, multi choice questions, and one word answer
	 * questions.
	 */

	// public QuestionCollectionPanel
	// displayQuestionAnswerPanel(QuestionsAndAnswers record){
	public QuestionCollectionPanel(QuestionsAndAnswers record) {
		this.setBorder(new EmptyBorder(10, 25, 20, 50));
		questionRecord = record;

		JCheckBox[] optionsCheckBoxes;
		// questionCollectionPanel = new QuestionCollectionPanel();
		this.setLayout(new BorderLayout(0, 0));

		questionPanel = new JPanel(new BorderLayout());
		this.add(questionPanel, BorderLayout.NORTH);

		optionsCollectionPanel = new JPanel(new CardLayout());
		this.add(optionsCollectionPanel, BorderLayout.CENTER);

		// timerPanel = new ProgressBarPanel();
		progressBar = new JProgressBar(0, 60);
		progressBar.setStringPainted(false);
		this.add(progressBar, BorderLayout.SOUTH);
		progressValue = 1;
		startTimer();

		displayQuestionRecord();
		// return this;

	}

	private void displayQuestionRecord() {

		/*
		 * Display the question statement
		 */
		JLabel questionLabel = new JLabel();
		String question = "<html><p><h3>" + questionRecord.getQuestion() + "</h3></p></html>";
		questionLabel.setText(question);
		// Font labelFont = questionLabel.getFont();
		// questionLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 14));
		questionPanel.add(questionLabel);

		/*
		 * Display the correct answer
		 */
		/*
		 * ArrayList<String> answer = (ArrayList<String>)
		 * questionRecord.getAnswers(); bottomAnswer$ButtonPanel =
		 * createBottomAnswer$ButtonPanel(answer);
		 * questionCollectionPanel.add(bottomAnswer$ButtonPanel,
		 * BorderLayout.SOUTH); currentAnswerStatus = AnswerStatus.UNKNOWN;
		 */

		/*
		 * Display the options
		 */
		Enum<AnswerType> answerType = questionRecord.answerType;
		CardLayout cd = (CardLayout) optionsCollectionPanel.getLayout();
		if (answerType == AnswerType.TEXTUAL) {
			JPanel imageQuesAnsPanel = createOneWordAnswerPanel();
			optionsCollectionPanel.add(imageQuesAnsPanel, "imageQuesAnsPanel");
			cd.show(optionsCollectionPanel, "imageQuesAnsPanel");
		} else {
			ArrayList<String> options = (ArrayList<String>) questionRecord.getOptions();
			if (answerType.equals(AnswerType.SINGLE)) {
				JPanel radioButtonOptionsCardPanel = createRadioButtonOptionsCardPanel(options);
				optionsCollectionPanel.add(radioButtonOptionsCardPanel, "radioButtonOptionsCardPanel");
				cd.show(optionsCollectionPanel, "radioButtonOptionsCardPanel");
			} else if (answerType.equals(AnswerType.MULTIPLE)) {
				JPanel checkBoxOptionsCardPanel = createCheckBoxOptionsCardPanel(options);
				optionsCollectionPanel.add(checkBoxOptionsCardPanel, "checkBoxOptionsCardPanel");
				cd.show(optionsCollectionPanel, "checkBoxOptionsCardPanel");
			}
		}
	}

	/**
	 * Returns the answer in readable format
	 * 
	 * @param ArrayList<String> : List of correct answers for the given question
	 * 
	 * @return String: the correct answer for the displayed/current question
	 */
	private static String getAnswerText(ArrayList<String> answerList) {
		StringBuffer answerText = new StringBuffer();
		answerText.append("<html><p>");
		int len = answerList.size();
		for (int i = 0; i < len; i++) {
			if (i > 0)
				answerText.append(", ");
			answerText.append(answerList.get(i));
		}
		answerText.append("</p></html>");
		return answerText.toString();

	}

	/**
	 * Initializes the content of createRadioButtonOptionsCardPanel. This Panel
	 * has a group of Radio button that display the options for the given
	 * question.
	 * 
	 * @param ArrayList<String> : List of options to be displayed.
	 * 
	 * @return JPanel : The panel containing the options in the form of radio
	 * buttons.
	 */

	private JPanel createRadioButtonOptionsCardPanel(ArrayList<String> options) {

		JPanel radioButtonOptionCardPanel = new JPanel();
		radioButtonOptionCardPanel.setLayout(new BoxLayout(radioButtonOptionCardPanel, BoxLayout.Y_AXIS));
		// Create the radio buttons according to the number of options
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		int numOfOptions = options.size();
		for (int i = 0; i < numOfOptions; i++) {
			String optionText = options.get(i);
			JRadioButton optionButton = new JRadioButton(optionText);
			optionButton.setActionCommand(optionText);
			optionButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parentPanel.checkRadioButtonAnswer(e.getActionCommand());
				}
			});
			group.add(optionButton);
			radioButtonOptionCardPanel.add(optionButton);
		}
		return radioButtonOptionCardPanel;

	}

	/**
	 * Initializes the content of createcheckBoxOptionsCardPanel. This Panel has
	 * a group of check box button that display the options for the given
	 * question.
	 * 
	 * @param ArrayList<String> : List of options to be displayed.
	 * 
	 * @return JPanel : The panel containing the options in the form of
	 * checkBoxes
	 */

	private JPanel createCheckBoxOptionsCardPanel(ArrayList<String> options) {

		JPanel checkBoxOptionsCardPanel = new JPanel();
		checkBoxOptionsCardPanel.setLayout(new BoxLayout(checkBoxOptionsCardPanel, BoxLayout.Y_AXIS));

		// Create check box buttons
		int numOfOptions = options.size();
		optionsCheckBoxes = new JCheckBox[numOfOptions];
		for (int i = 0; i < numOfOptions; i++) {
			String optionText = options.get(i);
			optionsCheckBoxes[i] = new JCheckBox(optionText);
			optionsCheckBoxes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkCheckBoxAnswer();
				}
			});
			checkBoxOptionsCardPanel.add(optionsCheckBoxes[i]);

		}
		return checkBoxOptionsCardPanel;
	}

	public void setParentPanel(CentralQuizCardPanel centralQuizCardPanel) {

		parentPanel = centralQuizCardPanel;
	}

	/*
	 * Helper method that is called everytime an option is selected and checks
	 * if its the right answer.
	 */

	private void checkCheckBoxAnswer() {

		int numOfOptions = questionRecord.getOptions().size();
		List<String> selectedAnswer = new ArrayList<String>();
		List<String> actualAnswers = new ArrayList<String>();
		actualAnswers = questionRecord.getAnswers();
		int correctNumAns = actualAnswers.size();

		for (int i = 0; i < numOfOptions; i++) {

			if (optionsCheckBoxes[i].isSelected()) {
				//System.out.println(optionsCheckBoxes[i].getText());
				selectedAnswer.add(optionsCheckBoxes[i].getText());
			}
		}
		parentPanel.checkCheckBoxAnswer((ArrayList<String>) selectedAnswer);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void startTimer() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				progressBar.setValue(progressValue++);
				if (progressValue > 60) {
					timer.stop();
					parentPanel.checkAnswer();
					parentPanel.getQuestion("NEXT");
				}
			}
		});
		timer.start();
	}

	/*
	 * Creates the panel for displaying the question image and a text field fkor
	 * entering the answer.
	 */

	private JPanel createOneWordAnswerPanel() {

		JPanel imageQuesAnsPanel = new JPanel(new BorderLayout());
		
		ImageIcon ic = createImageIcon("/resources/" + questionRecord.getOptions().get(0));
		String answer = questionRecord.getAnswers().get(0);
		JScrollPane jsp = new JScrollPane(new JLabel(ic));
		imageQuesAnsPanel.add(jsp, BorderLayout.CENTER);
		JPanel answerPanel = new JPanel();
		JLabel answerLabel = new JLabel("Answer");
		answerPanel.add(answerLabel);
		JTextField answerTextField = new JTextField();
		answerTextField.setColumns(5);
		answerTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (answerTextField.getText().length() > answer.length()) {
					e.consume();
				}
			}
		});
		answerTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				parentPanel.checkOneWrdAnswer(answerTextField.getText());
			}

		});
		answerPanel.add(answerTextField);
		imageQuesAnsPanel.add(answerPanel, BorderLayout.SOUTH);

		return imageQuesAnsPanel;
	}

	public Timer getTimer() {
		return timer;
	}
}
