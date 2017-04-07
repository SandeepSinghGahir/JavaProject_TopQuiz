package guiComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * BottomButtonPanel.java
 * 
 * @author swabhat This class displays the "Next" and "Submit" button after the
 *         start of the quiz. The "Next" button takes the user to the next
 *         question of the current topic. The "Submit" button ends the quiz and
 *         displays the result.
 */
public class BottomButtonPanel extends JPanel {

	private CentralQuizCardPanel parentPanel;

	public BottomButtonPanel() {

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentPanel.checkAnswer();
				// changeCardToNextQuestion();
				parentPanel.getQuestion(e.getActionCommand());
			}
		});
		this.add(btnNext);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentPanel.checkAnswer();
				parentPanel.showResult();
			}
		});
		this.add(submitButton);
	}

	/*
	 * It sets the CentralQuizCardPanel as its containing panel.
	 * 
	 * @param centralQuizCardPanel parent panel of buttons panel
	 */

	public void setParentPanel(CentralQuizCardPanel centralQuizCardPanel) {
		this.parentPanel = centralQuizCardPanel;
	}

	/*
	 * It gets the CentralQuizCardPanel that contains it.
	 * 
	 * @return centralQuizCardPanel parent panel of buttons panel
	 */
	public CentralQuizCardPanel getParentPanel() {
		return parentPanel;
	}
}
