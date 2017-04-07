package guiComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * WelcomeCardPanel.java
 * 
 * @author swabhat This is the starting screen of the app. It explains the rules
 *         of the quiz and has a "Start" button to start the quiz.
 */
public class WelcomeCardPanel extends JPanel {

	private JPanel parentPanel;

	/**
	 * It initializes the starting page of the app. It has a welcome title and
	 * the rule for the quiz
	 */
	public WelcomeCardPanel() {

		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(0, 20, 10, 30));

		String ruleslabelText = "<html><h2>"
				+ "<p align = \"justify\"><ul><li>To begin, clcik on Start and then select a topic of your choice.</li><br>"
				+ "<li>You will have 1 minute to answer each question</li><br>"
				+ "<li>After the time limit, you will be taken to the next question. </li>"
				+ "<li>You can anytime click on <i>next</i> to view the next question or click on <i>Submit</i> to view the result</li><br>"
				+ "<li>You can anytime change the subject by selecting the topic of your choice</li><br>"
				+ "<li>After attempting all the questions of a topic or by clicking on <i>Submit</i>, you will be able to view the result.</li></ul></p></h2></html>";
		JLabel rulesLabel = new JLabel(ruleslabelText);
		rulesLabel.setVerticalAlignment(SwingConstants.TOP);
		this.add(rulesLabel);

		JPanel startButtonpanel = new JPanel();
		this.add(startButtonpanel, BorderLayout.SOUTH);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println(e.getActionCommand() + " has been selected");
				// Pass the job of starting the Quiz to parentPanel
				((MainCollectionPanel) parentPanel).startQuiz();

			}
		});
		startButton.setToolTipText("Start Quiz");
		startButtonpanel.add(startButton);

	}

	public void setParentPanel(JPanel mainFrameCentralPanel) {
		this.parentPanel = mainFrameCentralPanel;
	}

	public JPanel getParentPanel() {
		return parentPanel;
	}
}
